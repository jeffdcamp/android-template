package org.jdc.template.ux.individualedit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jdc.template.R
import org.jdc.template.ui.DateUiUtil
import org.jdc.template.ui.compose.Hoisted
import org.jdc.template.ui.compose.SimpleDialog
import org.jdc.template.ui.compose.hoist
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun IndividualEditScreen() {
    val viewModel: IndividualEditViewModel = viewModel()

    val firstName: String by viewModel.firstNameFlow.collectAsState()
    val lastName: String by viewModel.lastNameFlow.collectAsState()
    val phoneNumber: String by viewModel.phoneNumberFlow.collectAsState()
    val email: String by viewModel.emailFlow.collectAsState()
    val birthDate: LocalDate? by viewModel.birthDateFlow.collectAsState(null)
    val alarmTime: LocalTime? by viewModel.alarmTimeFlow.collectAsState(null)

    IndividualEditFields(
        firstName.hoist(viewModel::setFirstName),
        lastName.hoist(viewModel::setLastName),
        phoneNumber.hoist(viewModel::setPhoneNumber),
        email.hoist(viewModel::setEmail),
        birthDate.hoist(viewModel::setBirthDate),
        alarmTime.hoist(viewModel::setAlarmTime),
        viewModel::onBirthDateClicked,
        viewModel::onAlarmTimeClicked
    )

    val simpleDialogData by viewModel.simpleDialogData.collectAsState()
    SimpleDialog(simpleDialogData.visible, title = simpleDialogData.title, text = simpleDialogData.text, onDismissRequest = viewModel::hideInfoDialog,  onConfirmButtonClicked = viewModel::hideInfoDialog)
}

@Composable
private fun IndividualEditFields(
    firstNameHoisted: Hoisted<String>,
    lastNameHoisted: Hoisted<String>,
    phoneNumberHoisted: Hoisted<String>,
    emailHoisted: Hoisted<String>,
    birthDateHoisted: Hoisted<LocalDate?>,
    alarmTimeHoisted: Hoisted<LocalTime?>,
    onBirthdayClicked: () -> Unit,
    onAlarmClicked: () -> Unit
) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        IndividualEditField(stringResource(R.string.first_name), firstNameHoisted)
        IndividualEditField(stringResource(R.string.last_name), lastNameHoisted)
        IndividualEditField(stringResource(R.string.phone), phoneNumberHoisted)
        IndividualEditField(stringResource(R.string.email), emailHoisted)

        IndividualClickableEditField(stringResource(R.string.birth_date), DateUiUtil.getLocalDateText(LocalContext.current, birthDateHoisted.value), onBirthdayClicked)
        IndividualClickableEditField(stringResource(R.string.alarm_time), DateUiUtil.getLocalTimeText(LocalContext.current, alarmTimeHoisted.value), onAlarmClicked)
    }
}

@Composable
private fun IndividualEditField(label: String, text: Hoisted<String>) {
    TextField(
        value = text.value,
        onValueChange = { text.onChange(it) },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    )
}

@Composable
private fun IndividualClickableEditField(label: String, text: String, onClick: () -> Unit) {
    TextField(
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
