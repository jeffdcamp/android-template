package org.jdc.template.ux.individualedit

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.datetime.timePicker
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.vikingsen.inject.viewmodel.savedstate.SavedStateViewModelFactory
import org.jdc.template.R
import org.jdc.template.databinding.IndividualEditBinding
import org.jdc.template.ext.toCalendar
import org.jdc.template.ext.toLocalDate
import org.jdc.template.ext.toLocalTime
import org.jdc.template.inject.Injector
import org.jdc.template.ui.fragment.LiveDataObserverFragment
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

class IndividualEditFragment : LiveDataObserverFragment() {

    @Inject
    lateinit var viewModelFactoryFactory: SavedStateViewModelFactory.Factory

    private val viewModel by viewModels<IndividualEditViewModel> { viewModelFactoryFactory.create(this, requireArguments()) }
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
        binding.viewModel = this@IndividualEditFragment.viewModel
        binding.lifecycleOwner = this@IndividualEditFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.individual)

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        // Events
        viewModel.onIndividualSavedEvent.observeKt { findNavController().popBackStack() }
        viewModel.onShowBirthDateSelectionEvent.observeKt { showBirthDateSelector(it) }
        viewModel.onShowAlarmTimeSelectionEvent.observeKt { showAlarmTimeSelector(it) }

        viewModel.onValidationSaveErrorEvent.observeKt {
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
        MaterialDialog(requireContext()).show {
            datePicker(currentDate = date.toCalendar()) { _, newDate ->
                viewModel.birthDate.set(newDate.toLocalDate())
            }
        }
    }

    private fun showAlarmTimeSelector(time: LocalTime) {
        MaterialDialog(requireContext()).show {
            lifecycleOwner(this@IndividualEditFragment)
            timePicker(currentTime = time.toCalendar(), show24HoursView = false) { _, newTime ->
                viewModel.alarmTime.set(newTime.toLocalTime())
            }
        }
    }
}