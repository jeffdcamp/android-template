package org.jdc.template.ux.individualedit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.databinding.IndividualEditFragmentBinding
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.ui.DateUiUtil
import org.jdc.template.util.ext.autoCleared
import org.jdc.template.util.ext.withLifecycleOwner
import java.time.LocalDate
import java.time.LocalTime

@AndroidEntryPoint
class IndividualEditFragment : Fragment() {
    private val viewModel: IndividualEditViewModel by viewModels()

    private var binding: IndividualEditFragmentBinding by autoCleared()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = IndividualEditFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        withLifecycleOwner(this) {
            viewModel.loadedIndividualFlow.collectWhenStarted { individual -> showIndividual(individual) }
            viewModel.birthDateTextFlow.collectWhenStarted { birthDate -> binding.birthDateEditText.setText(DateUiUtil.getLocalDateText(requireContext(), birthDate)) }
            viewModel.alarmTimeTextFlow.collectWhenStarted { alarmTime -> binding.alarmTimeEditText.setText(DateUiUtil.getLocalTimeText(requireContext(), alarmTime)) }

            // Events
            viewModel.eventChannel.receiveWhenStarted { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: IndividualEditViewModel.Event) {
        when (event) {
            is IndividualEditViewModel.Event.IndividualSaved -> findNavController().popBackStack()
            is IndividualEditViewModel.Event.ShowBirthDateSelection -> showBirthDateSelector(event.date)
            is IndividualEditViewModel.Event.ShowAlarmTimeSelection -> showAlarmTimeSelector(event.time)
            is IndividualEditViewModel.Event.ValidationSaveError -> {
                when (event.error) {
                    IndividualEditViewModel.FieldValidationError.FIRST_NAME_REQUIRED -> binding.firstNameLayout.error = getString(event.error.errorMessageId)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.individual_edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_save -> {
                viewModel.saveIndividual(
                    firstName = binding.firstNameEditText.text.toString(),
                    lastName = binding.lastNameEditText.text.toString(),
                    phone = binding.phoneEditText.text.toString(),
                    email = binding.emailEditText.text.toString()
                )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupClickListeners() {
        binding.birthDateEditText.setOnClickListener { viewModel.onBirthDateClicked() }
        binding.alarmTimeEditText.setOnClickListener { viewModel.onAlarmTimeClicked() }
    }

    private fun showIndividual(individual: Individual) {
        binding.firstNameEditText.setText(individual.firstName ?: "")
        binding.lastNameEditText.setText(individual.lastName ?: "")
        binding.phoneEditText.setText(individual.phone ?: "")
        binding.emailEditText.setText(individual.email ?: "")

        // set UI from Flow
        // binding.birthDateEditText.setText(DateUiUtil.getLocalDateText(requireContext(), individual.birthDate))
        // binding.alarmTimeEditText.setText(DateUiUtil.getLocalTimeText(requireContext(), individual.alarmTime))
    }

    private fun showBirthDateSelector(date: LocalDate) {
        val birthDatePickerDialog = DatePickerDialog(requireActivity(), { _, year, monthOfYear, dayOfMonth ->
            viewModel.setBirthDate(LocalDate.of(year, monthOfYear + 1, dayOfMonth)) // + 1 because core Java Date is 0 based
        }, date.year, date.monthValue - 1, date.dayOfMonth) // - 1 because core Java Date is 0 based

        birthDatePickerDialog.show()
    }

    private fun showAlarmTimeSelector(time: LocalTime) {
        val alarmTimePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
            viewModel.setAlarmTime(LocalTime.of(hourOfDay, minute))
        }, time.hour, time.minute, false)

        alarmTimePickerDialog.show()
    }
}