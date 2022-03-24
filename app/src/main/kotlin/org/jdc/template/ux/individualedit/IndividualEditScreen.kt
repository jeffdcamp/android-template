@file:Suppress("MatchingDeclarationName")
package org.jdc.template.ux.individualedit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.R
import org.jdc.template.ui.DateUiUtil
import org.jdc.template.ui.compose.DayNightTextField
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.compose.appbar.AppBarMenu
import org.jdc.template.ui.compose.appbar.AppBarMenuItem
import org.jdc.template.ui.compose.appbar.AppScaffold
import org.jdc.template.ui.compose.dialog.MaterialDatePickerDialog
import org.jdc.template.ui.compose.dialog.MaterialTimePickerDialog
import org.jdc.template.ui.compose.dialog.MessageDialog
import org.jdc.template.ui.navigation.HandleNavigation
import java.time.LocalDate
import java.time.LocalTime

@Suppress("LongParameterList")
class IndividualState(
    val firstNameFlow: StateFlow<String>,
    val firstNameOnChange: (String) -> Unit,
    val lastNameFlow: StateFlow<String>,
    val lastNameOnChange: (String) -> Unit,
    val phoneFlow: StateFlow<String>,
    val phoneOnChange: (String) -> Unit,
    val emailFlow: StateFlow<String>,
    val emailOnChange: (String) -> Unit,
    val birthDateFlow: StateFlow<LocalDate?>,
    val birthDateClicked: () -> Unit,
    val alarmTimeFlow: StateFlow<LocalTime?>,
    val alarmTimeClicked: () -> Unit
)

@Composable
fun IndividualEditScreen(viewModel: IndividualEditViewModel = hiltViewModel()) {
    val navController = LocalNavController.current

    val individualState = IndividualState(
        viewModel.firstNameFlow,
        { viewModel.setFirstName(it) },
        viewModel.lastNameFlow,
        { viewModel.setLastName(it) },
        viewModel.phoneNumberFlow,
        { viewModel.setPhoneNumber(it) },
        viewModel.emailFlow,
        { viewModel.setEmail(it) },
        viewModel.birthDateFlow,
        { viewModel.onBirthDateClicked() },
        viewModel.alarmTimeFlow,
        { viewModel.onAlarmTimeClicked() }
    )

    val appBarMenuItems = listOf(
        AppBarMenuItem.Text(stringResource(R.string.save)) { viewModel.saveIndividual() }
    )

    AppScaffold(
        title = stringResource(R.string.edit_individual),
        actions = { AppBarMenu(appBarMenuItems) }
    ) {
        IndividualEditFields(individualState)
    }

    val messageDialogData by viewModel.messageDialogDataFlow.collectAsState()
    if (messageDialogData.visible) {
        MessageDialog(
            title = messageDialogData.title,
            text = messageDialogData.text,
            onDismissRequest = viewModel::hideInfoDialog,
            onConfirmButtonClicked = viewModel::hideInfoDialog
        )
    }

    BirthDateDialog(viewModel.showBirthDateFlow, { viewModel.resetShowBirthDate() }) {
        viewModel.setBirthDate(it)
        viewModel.resetShowBirthDate()
    }

    AlarmTimeDialog(viewModel.showAlarmTimeFlow, { viewModel.resetShowAlarmTime() }) {
        viewModel.setAlarmTime(it)
        viewModel.resetShowAlarmTime()
    }

    HandleNavigation(viewModel, navController, viewModel.navigatorFlow)
}

@Composable
fun IndividualEditFields(
    individualState: IndividualState,
) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        IndividualEditField(stringResource(R.string.first_name), individualState.firstNameFlow, "firstNameEditTextTag", individualState.firstNameOnChange)
        IndividualEditField(stringResource(R.string.last_name), individualState.lastNameFlow, "lastNameEditTextTag", individualState.lastNameOnChange)
        IndividualEditField(stringResource(R.string.phone), individualState.phoneFlow, "phoneEditTextTag", individualState.phoneOnChange)
        IndividualEditField(stringResource(R.string.email), individualState.emailFlow, "emailEditTextTag", individualState.emailOnChange)

        DateClickableEditField(stringResource(R.string.birth_date), individualState.birthDateFlow, individualState.birthDateClicked)
        TimeClickableEditField(stringResource(R.string.birth_date), individualState.alarmTimeFlow, individualState.alarmTimeClicked)
    }
}

@Composable
private fun IndividualEditField(label: String, textFlow: StateFlow<String>, testTag: String, onChange: (String) -> Unit) {
    val text by textFlow.collectAsState()

    DayNightTextField(
        value = text,
        onValueChange = { onChange(it) },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .testTag(testTag)
    )
}

@Composable
private fun DateClickableEditField(label: String, localDateFlow: StateFlow<LocalDate?>, onClick: () -> Unit) {
    val date by localDateFlow.collectAsState()
    val text = DateUiUtil.getLocalDateText(LocalContext.current, date)
    IndividualClickableEditField(label, text, onClick)
}

@Composable
private fun TimeClickableEditField(label: String, localTimeFlow: StateFlow<LocalTime?>, onClick: () -> Unit) {
    val time by localTimeFlow.collectAsState()
    val text = DateUiUtil.getLocalTimeText(LocalContext.current, time)
    IndividualClickableEditField(label, text, onClick)
}

@Composable
private fun IndividualClickableEditField(label: String, text: String, onClick: () -> Unit) {
    DayNightTextField(
        value = text,
        onValueChange = { },
        enabled = false,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable(
                onClick = {
                    onClick()
                }
            )
    )
}

@Composable
fun BirthDateDialog(
    showBirthDateFlow: StateFlow<LocalDate?>,
    onDismiss: (() -> Unit)? = null,
    onDateSelected: (LocalDate) -> Unit,
) {
    val showBirthDate by showBirthDateFlow.collectAsState()

    showBirthDate?.let { birthDate ->
        MaterialDatePickerDialog(date = birthDate, onDismiss = onDismiss, onDateSelected = onDateSelected)
    }
}

@Composable
fun AlarmTimeDialog(
    showAlarmTimeFlow: StateFlow<LocalTime?>,
    onDismiss: (() -> Unit)? = null,
    onTimeSelected: (LocalTime) -> Unit,
) {
    val showAlarmDate by showAlarmTimeFlow.collectAsState()

    showAlarmDate?.let { time ->
        MaterialTimePickerDialog(time = time, onDismiss = onDismiss, onTimeSelected = onTimeSelected)
    }
}

