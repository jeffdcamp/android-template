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
import androidx.compose.Composable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.clickable
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.material.OutlinedTextField
import androidx.ui.res.stringResource
import androidx.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.ui.compose.AppTheme
import org.jdc.template.ui.compose.setContent
import org.jdc.template.ui.fragment.BaseFragment
import java.time.LocalDate
import java.time.LocalTime

@AndroidEntryPoint
class IndividualEditFragment : BaseFragment() {
    private val viewModel: IndividualEditViewModel by viewModels()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return setContent {
            IndividualEditPage(viewModel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        // Events
        lifecycleScope.launchWhenStarted {
            for (event in viewModel.eventChannel) {
                when (event) {
                    is IndividualEditViewModel.Event.IndividualSaved -> findNavController().popBackStack()
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

    private fun showBirthDateSelector(date: LocalDate) {
        val birthDatePickerDialog = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            viewModel.birthDate.value = LocalDate.of(year, monthOfYear + 1, dayOfMonth) // + 1 because core Java Date is 0 based
        }, date.year, date.monthValue - 1, date.dayOfMonth) // - 1 because core Java Date is 0 based

        birthDatePickerDialog.show()
    }

    private fun showAlarmTimeSelector(time: LocalTime) {
        val alarmTimePickerDialog = TimePickerDialog(requireActivity(), TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.alarmTime.value = LocalTime.of(hourOfDay, minute)
        }, time.hour, time.minute, false)

        alarmTimePickerDialog.show()
    }

    @Composable
    fun IndividualEditPage(viewModel: IndividualEditViewModel) {
        AppTheme {
            VerticalScroller {
                Column(Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = viewModel.firstName.value,
                        onValueChange = { viewModel.firstName.value = it },
                        label = { Text(text = stringResource(id = R.string.first_name)) },
                        isErrorValue = viewModel.firstNameError.value
                    )
                    OutlinedTextField(value = viewModel.lastName.value, onValueChange = { viewModel.lastName.value = it }, label = { Text(text = stringResource(id = R.string.last_name)) })
                    OutlinedTextField(value = viewModel.phone.value, onValueChange = { viewModel.phone.value = it }, label = { Text(text = stringResource(id = R.string.phone)) })
                    OutlinedTextField(value = viewModel.email.value, onValueChange = { viewModel.email.value = it }, label = { Text(text = stringResource(id = R.string.email)) })
                    Box(modifier = Modifier.clickable(onClick = { showBirthDateSelector(viewModel.birthDate.value ?: LocalDate.now()) })) {
                        OutlinedTextField(
                            value = viewModel.birthDateFormatted.value,
                            onValueChange = {},
                            label = { Text(text = stringResource(id = R.string.birth_date)) }
                        )
                    }
                    Box(modifier = Modifier.clickable(onClick = { showAlarmTimeSelector(viewModel.alarmTime.value) })) {
                        OutlinedTextField(
                            value = viewModel.alarmTimeFormatted.value,
                            onValueChange = {},
                            label = { Text(text = stringResource(id = R.string.alarm_time)) }
                        )
                    }
                }
            }
        }
    }
}
