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
import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.R
import org.jdc.template.ui.DateUiUtil
import org.jdc.template.ui.compose.DayNightTextField
import org.jdc.template.ui.compose.appbar.AppBarMenu
import org.jdc.template.ui.compose.appbar.AppBarMenuItem
import org.jdc.template.ui.compose.dialog.HandleDialog
import org.jdc.template.ui.compose.dialog.MaterialDatePickerDialog
import org.jdc.template.ui.compose.dialog.MaterialTimePickerDialog
import org.jdc.template.ui.compose.dialog.MessageDialog
import org.jdc.template.ui.navigation.HandleNavigation
import org.jdc.template.ux.MainAppScaffoldWithNavBar
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun IndividualEditScreen(
    navController: NavController,
    viewModel: IndividualEditViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    val appBarMenuItems = listOf(
        AppBarMenuItem.Text(stringResource(R.string.save)) { uiState.saveIndividual() }
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.edit_individual),
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStack() },
    ) {
        IndividualEditFields(viewModel.uiState)
    }

    HandleDialog(uiState.messageDialogDataFlow) { dialogData ->
        MessageDialog(
            title = dialogData.title,
            text = dialogData.text,
            onDismissRequest = { uiState.hideMessageDialog() },
            onConfirmButtonClicked = { uiState.hideMessageDialog() }
        )
    }

    HandleDialog(uiState.birthDateDialogData) { dialogData ->
        dialogData.localDate?.let { date ->
            MaterialDatePickerDialog(date, { uiState.dismissBirthDateDialog() }) { uiState.onBirthDateSelected(it) }
        }
    }

    HandleDialog(uiState.alarmTimeDialogData) { dialogData ->
        dialogData.localTime?.let { time ->
            MaterialTimePickerDialog(time, { uiState.dismissAlarmTimeDialog() }) { uiState.onAlarmTimeSelected(it) }
        }
    }

    HandleNavigation(viewModel, navController)
}

@Composable
fun IndividualEditFields(
    uiState: IndividualEditUiState
) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        IndividualEditField(stringResource(R.string.first_name), uiState.firstNameFlow, "firstNameEditTextTag", uiState.firstNameOnChange)
        IndividualEditField(stringResource(R.string.last_name), uiState.lastNameFlow, "lastNameEditTextTag", uiState.lastNameOnChange)
        IndividualEditField(stringResource(R.string.phone), uiState.phoneFlow, "phoneEditTextTag", uiState.phoneOnChange)
        IndividualEditField(stringResource(R.string.email), uiState.emailFlow, "emailEditTextTag", uiState.emailOnChange)

        DateClickableEditField(stringResource(R.string.birth_date), uiState.birthDateFlow, uiState.birthDateClicked)
        TimeClickableEditField(stringResource(R.string.alarm_time), uiState.alarmTimeFlow, uiState.alarmTimeClicked)
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
