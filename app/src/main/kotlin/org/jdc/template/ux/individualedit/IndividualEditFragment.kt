package org.jdc.template.ux.individualedit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Box
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.ext.receiveWhenStarted
import org.jdc.template.ui.compose.AppTheme
import org.jdc.template.ui.compose.setContent
import org.jdc.template.ux.main.NavControllerAmbient
import java.time.LocalDate
import java.time.LocalTime

@AndroidEntryPoint
class IndividualEditFragment : Fragment() {
    private val viewModel: IndividualEditViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return setContent {
            Providers(NavControllerAmbient provides findNavController()) {
                IndividualEditPage()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        // Events
        viewLifecycleOwner.receiveWhenStarted(viewModel.eventChannel) { event ->
            when (event) {
                is IndividualEditViewModel.Event.ShowBirthDateSelection -> showBirthDateSelector(event.date)
                is IndividualEditViewModel.Event.ShowAlarmTimeSelection -> showAlarmTimeSelector(event.time)
//                is IndividualEditViewModel.Event.ValidationSaveError -> {
//                    when (event.error) {
//                        IndividualEditViewModel.FieldValidationError.FIRST_NAME_REQUIRED -> binding.firstNameLayout.error = getString(event.error.errorMessageId)
//                    }
//                }
            }
        }
    }

    private fun showBirthDateSelector(date: LocalDate) {
        val birthDatePickerDialog = DatePickerDialog(requireActivity(), { _, year, monthOfYear, dayOfMonth ->
            viewModel.birthDate.value = LocalDate.of(year, monthOfYear + 1, dayOfMonth) // + 1 because core Java Date is 0 based
        }, date.year, date.monthValue - 1, date.dayOfMonth) // - 1 because core Java Date is 0 based

        birthDatePickerDialog.show()
    }

    private fun showAlarmTimeSelector(time: LocalTime) {
        val alarmTimePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
            viewModel.alarmTime.value = LocalTime.of(hourOfDay, minute)
        }, time.hour, time.minute, false)

        alarmTimePickerDialog.show()
    }
}

@Composable
fun IndividualEditPage() {
    AppTheme {
        Scaffold(
            topBar = { AppBar() }
        ) { padding ->
            IndividualForm(Modifier.padding(padding))
        }
    }
}

@Composable
private fun AppBar() {
    val navController = NavControllerAmbient.current
    val viewModel: IndividualEditViewModel = viewModel()
    TopAppBar(
        title = {},
        actions = {
            Text(
                text = stringResource(R.string.save),
                modifier = Modifier.clickable(onClick = {
                    if (viewModel.saveIndividual()) {
                        navController.popBackStack()
                    }
                }),
            )
        }
    )
}

@Composable
private fun IndividualForm(modifier: Modifier = Modifier) {
    ScrollableColumn(modifier.padding(16.dp)) {
        val viewModel = viewModel<IndividualEditViewModel>()
        OutlinedTextField(
            value = viewModel.firstName.value,
            onValueChange = { viewModel.firstName.value = it },
            label = { Text(text = stringResource(id = R.string.first_name)) },
            isErrorValue = viewModel.firstNameError.value
        )
        OutlinedTextField(
            value = viewModel.lastName.value,
            onValueChange = { viewModel.lastName.value = it },
            label = { Text(text = stringResource(id = R.string.last_name)) },
            imeAction = ImeAction.Next,
        )
        OutlinedTextField(
            value = viewModel.phone.value,
            onValueChange = { viewModel.phone.value = it },
            label = { Text(text = stringResource(id = R.string.phone)) },
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Phone,
        )
        OutlinedTextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.email.value = it },
            label = { Text(text = stringResource(id = R.string.email)) },
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
        )
        Stack {
            OutlinedTextField(
                value = viewModel.birthDateFormatted.value,
                onValueChange = {},
                label = { Text(text = stringResource(id = R.string.birth_date)) },
            )
            Box(modifier = Modifier.matchParentSize().clickable(onClick = { viewModel.onBirthDateClicked() }))
        }
        Stack {
            OutlinedTextField(
                value = viewModel.alarmTimeFormatted.value,
                onValueChange = {},
                label = { Text(text = stringResource(id = R.string.alarm_time)) },
            )
            Box(modifier = Modifier.matchParentSize().clickable(onClick = { viewModel.onAlarmTimeClicked() }))
        }
    }
}
