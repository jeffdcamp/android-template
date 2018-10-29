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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import org.jdc.template.R
import org.jdc.template.databinding.IndividualEditBinding
import org.jdc.template.inject.Injector
import org.jdc.template.ui.fragment.LiveDataObserverFragment
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

class IndividualEditFragment : LiveDataObserverFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy<IndividualEditViewModel> { ViewModelProviders.of(this, viewModelFactory).get() }
    private lateinit var binding: IndividualEditBinding

    init {
        Injector.get().inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.individual_edit, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = this@IndividualEditFragment.viewModel
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.individual)

        setupViewModelObservers()

        val args = IndividualEditFragmentArgs.fromBundle(arguments)

        viewModel.loadIndividual(args.individualId)
    }

    private fun setupViewModelObservers() {
        // Events
        viewModel.onIndividualSavedEvent.observe { this@IndividualEditFragment.findNavController().popBackStack() }
        viewModel.onShowBirthDateSelectionEvent.observeNotNull { showBirthDateSelector(it) }
        viewModel.onShowAlarmTimeSelectionEvent.observeNotNull { showAlarmTimeSelector(it) }

        viewModel.onValidationSaveErrorEvent.observeNotNull {
            when (it) {
                IndividualEditViewModel.FieldValidationError.FIRST_NAME_REQUIRED -> binding.firstNameLayout.error = getString(it.errorMessageId)
                else -> { }
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
                viewModel.saveIndividual()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showBirthDateSelector(date: LocalDate) {
        val birthDatePickerDialog = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            viewModel.birthDate.set(LocalDate.of(year, monthOfYear + 1, dayOfMonth)) // + 1 because cord Java Date is 0 based
        }, date.year, date.monthValue - 1, date.dayOfMonth) // - 1 because cord Java Date is 0 based

        birthDatePickerDialog.show()
    }

    private fun showAlarmTimeSelector(time: LocalTime) {
        val alarmTimePickerDialog = TimePickerDialog(requireActivity(), TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.alarmTime.set(LocalTime.of(hourOfDay, minute))
        }, time.hour, time.minute, false)

        alarmTimePickerDialog.show()
    }
}