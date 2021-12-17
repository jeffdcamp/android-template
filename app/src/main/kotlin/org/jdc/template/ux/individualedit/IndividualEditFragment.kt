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
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.util.ext.withLifecycleOwner
import java.time.LocalDate
import java.time.LocalTime

@AndroidEntryPoint
class IndividualEditFragment : Fragment() {
    private val viewModel: IndividualEditViewModel by viewModels()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CompositionLocalProvider(LocalNavController provides findNavController()) {
                    AppTheme {
                        Surface {
                            IndividualEditScreen()
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        withLifecycleOwner(this) {
            viewModel.navigateRouteFlow.collectWhenStarted { navigationData -> navigationData?.navigate(findNavController(), viewModel) }
            viewModel.showBirthDateFlow.collectWhenStarted {
                it?.let { date ->
                    showBirthDateSelector(date)
                    viewModel.resetShowBirthDate(date)
                }
            }
            viewModel.showAlarmTimeFlow.collectWhenStarted {
                it?.let { time ->
                    showAlarmTimeSelector(time)
                    viewModel.resetShowAlarmTime(time)
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
                viewModel.saveIndividual()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // TODO: Move to Compose when DatePickers are available for Compose (https://material.io/blog/jetpack-compose-beta)
    private fun showBirthDateSelector(date: LocalDate) {
        val birthDatePickerDialog = DatePickerDialog(requireActivity(), { _, year, monthOfYear, dayOfMonth ->
            viewModel.setBirthDate(LocalDate.of(year, monthOfYear + 1, dayOfMonth)) // + 1 because core Java Date is 0 based
        }, date.year, date.monthValue - 1, date.dayOfMonth) // - 1 because core Java Date is 0 based

        birthDatePickerDialog.show()
    }

    // TODO: Move to Compose when TimePickers are available for Compose (https://material.io/blog/jetpack-compose-beta)
    private fun showAlarmTimeSelector(time: LocalTime) {
        val alarmTimePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
            viewModel.setAlarmTime(LocalTime.of(hourOfDay, minute))
        }, time.hour, time.minute, false)

        alarmTimePickerDialog.show()
    }
}