@file:Suppress("MatchingDeclarationName")
package org.jdc.template.ux.individualedit

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import org.jdc.template.ui.compose.dialog.HandleDialogUiState
import org.jdc.template.ui.compose.util.formKeyEventHandler
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
        AppBarMenuItem.Text(R.string.save) { uiState.saveIndividual() }
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.edit_individual),
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStack() },
    ) {
        IndividualEditFields(viewModel.uiState)
    }

    HandleDialogUiState(uiState.dialogUiStateFlow)

    HandleNavigation(viewModel, navController)
}

@Composable
fun IndividualEditFields(
    uiState: IndividualEditUiState
) {
    val (firstNameFocus) = remember { FocusRequester.createRefs() }

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
    val focusManager = LocalFocusManager.current

    DayNightTextField(
        value = text,
        onValueChange = { onChange(it) },
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
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
    val source = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    DayNightTextField(
        value = text,
        onValueChange = { },
        readOnly = true,
        label = { Text(label) },
        interactionSource = source,
        modifier = Modifier
            .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    )

    if ( source.collectIsPressedAsState().value) {
        onClick()
    }
}
