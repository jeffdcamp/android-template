package org.jdc.template.ux.individualedit

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.ext.receiveWhenStarted
import org.jdc.template.ui.compose.AppTheme
import org.jdc.template.ui.compose.setContent
import org.jdc.template.ux.main.NavControllerAmbient
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

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
            }
        }
    }

    private fun showBirthDateSelector(date: LocalDate) {
        val selection = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val constraints = CalendarConstraints.Builder()
            .setOpenAt(selection)
            .setEnd(Clock.systemDefaultZone().millis())
            .build()
        val picker = MaterialDatePicker.Builder
            .datePicker()
            .setSelection(selection)
            .setCalendarConstraints(constraints)
            .build()
        picker.addOnPositiveButtonClickListener {
            viewModel.birthDate.value = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
        }
        picker.show(parentFragmentManager, picker.toString())
    }

    private fun showAlarmTimeSelector(time: LocalTime) {
        val picker = MaterialTimePicker()
        picker.setListener {
            viewModel.alarmTime.value = LocalTime.of(it.hour, it.minute)
        }
        picker.show(parentFragmentManager, picker.toString())
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
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack)
            }
        },
        title = {
            Text(stringResource(R.string.edit_individual))
        },
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
            imeAction = ImeAction.Next,
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
            val birthDate = viewModel.birthDateMillis?.let { DateUtils.formatDateTime(ContextAmbient.current, it, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR) } ?: ""
            OutlinedTextField(
                value = birthDate,
                onValueChange = {},
                label = { Text(text = stringResource(id = R.string.birth_date)) },
            )
            Box(modifier = Modifier.matchParentSize().clickable(onClick = { viewModel.onBirthDateClicked() }))
        }
        Stack {
            val alarmTime = DateUtils.formatDateTime(ContextAmbient.current, viewModel.alarmTimeMillis, DateUtils.FORMAT_SHOW_TIME)
            OutlinedTextField(
                value = alarmTime,
                onValueChange = {},
                label = { Text(text = stringResource(id = R.string.alarm_time)) },
            )
            Box(modifier = Modifier.matchParentSize().clickable(onClick = { viewModel.onAlarmTimeClicked() }))
        }
    }
}
