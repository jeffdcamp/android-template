@file:Suppress("MatchingDeclarationName")

package org.jdc.template.ux.individualedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import org.jdc.template.R
import org.jdc.template.shared.model.domain.type.IndividualType
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.compose.appbar.AppBarMenu
import org.jdc.template.ui.compose.appbar.AppBarMenuItem
import org.jdc.template.ui.compose.dialog.HandleDialogUiState
import org.jdc.template.ui.compose.form.DateClickableTextField
import org.jdc.template.ui.compose.form.DropdownMenuBoxField
import org.jdc.template.ui.compose.form.FlowTextField
import org.jdc.template.ui.compose.form.SwitchField
import org.jdc.template.ui.compose.form.TimeClickableTextField
import org.jdc.template.ui.compose.util.formKeyEventHandler
import org.jdc.template.ui.navigation3.HandleNavigation3
import org.jdc.template.ui.navigation3.navigator.Navigation3Navigator
import org.jdc.template.ui.strings.getStringResId
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun IndividualEditScreen(
    navigator: Navigation3Navigator,
    viewModel: IndividualEditViewModel
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    val appBarMenuItems = listOf(
        AppBarMenuItem.Text({ stringResource(R.string.save) }) { viewModel.onSaveClick() },
    )

    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = stringResource(R.string.edit_individual),
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navigator.pop() },
        hideNavigation = true
    ) {
        when (uiState) {
            IndividualEditUiState.Loading -> {}
            is IndividualEditUiState.Ready -> {
                IndividualEditContent(
                    uiState = uiState,
                    onFirstNameChange = viewModel::onFirstNameChange,
                    onLastNameChange = viewModel::onLastNameChange,
                    onPhoneChange = viewModel::onPhoneChange,
                    onEmailChange = viewModel::onEmailChange,
                    onBirthDateClick = viewModel::onBirthDateClick,
                    onAlarmTimeClick = viewModel::onAlarmTimeClick,
                    onIndividualTypeChange = viewModel::onIndividualTypeChange,
                    onAvailableChange = viewModel::onAvailableChange
                )
            }
            IndividualEditUiState.Empty -> {}
        }
    }

    HandleDialogUiState(viewModel.dialogUiStateFlow)

    HandleNavigation3(viewModel, navigator)
}

@Composable
fun IndividualEditContent(
    uiState: IndividualEditUiState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onBirthDateClick: () -> Unit,
    onAlarmTimeClick: () -> Unit,
    onIndividualTypeChange: (IndividualType) -> Unit,
    onAvailableChange: (Boolean) -> Unit
) {
    if (uiState !is IndividualEditUiState.Ready) {
        return
    }
    val formFields: IndividualEditFormFields = uiState.formFields

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        val focusManager = LocalFocusManager.current

        val fieldModifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)

        FlowTextField(stringResource(R.string.first_name), formFields.firstNameFlow, onFirstNameChange, fieldModifier.testTag(IndividualEditScreenFields.FIRST_NAME.name),
            formFields.firstNameErrorFlow)
        FlowTextField(stringResource(R.string.last_name), formFields.lastNameFlow, onLastNameChange, fieldModifier.testTag(IndividualEditScreenFields.LAST_NAME.name))
        FlowTextField(stringResource(R.string.phone), formFields.phoneNumberFlow, onPhoneChange, fieldModifier.testTag(IndividualEditScreenFields.PHONE.name))
        FlowTextField(stringResource(R.string.email), formFields.emailFlow, onEmailChange, fieldModifier.testTag(IndividualEditScreenFields.EMAIL.name), formFields.emailErrorFlow)

        DateClickableTextField(
            label = stringResource(R.string.birth_date),
            localDateFlow = formFields.birthDateFlow,
            onClick = onBirthDateClick,
            modifier = fieldModifier.testTag(IndividualEditScreenFields.BIRTH_DATE.name),
            errorTextFlow = formFields.birthDateErrorFlow
        )
        TimeClickableTextField(stringResource(R.string.alarm_time), formFields.alarmTimeFlow, onAlarmTimeClick, fieldModifier.testTag(IndividualEditScreenFields.ALARM_TIME.name))

        DropdownMenuBoxField(
            label = stringResource(R.string.individual_type),
            options = IndividualType.entries,
            selectedOptionFlow = formFields.individualTypeFlow,
            onOptionSelected = { onIndividualTypeChange(it) },
            optionToText = { stringResource(it.getStringResId()) },
            errorTextFlow = formFields.individualTypeErrorFlow,
            modifier = fieldModifier
                .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
                .testTag(IndividualEditScreenFields.TYPE.name)
        )

        SwitchField(stringResource(R.string.available), formFields.availableFlow, onAvailableChange, fieldModifier.testTag(IndividualEditScreenFields.AVAILABLE.name))
    }
}

enum class IndividualEditScreenFields {
    FIRST_NAME,
    LAST_NAME,
    PHONE,
    EMAIL,
    BIRTH_DATE,
    ALARM_TIME,
    TYPE,
    AVAILABLE
}

@PreviewDefault
@Composable
private fun Preview() {
    val uiState = IndividualEditUiState.Ready(
        IndividualEditFormFields(
            firstNameFlow = MutableStateFlow("Jeff"),
            firstNameErrorFlow = MutableStateFlow(null),
            lastNameFlow = MutableStateFlow("Campbell"),
            phoneNumberFlow = MutableStateFlow("801-555-0001"),
            emailFlow = MutableStateFlow("bob@bob.com"),
            emailErrorFlow = MutableStateFlow(null),
            birthDateFlow = MutableStateFlow(null),
            birthDateErrorFlow = MutableStateFlow(null),
            alarmTimeFlow = MutableStateFlow(null),
            individualTypeFlow = MutableStateFlow(IndividualType.UNKNOWN),
            individualTypeErrorFlow = MutableStateFlow(null),
            availableFlow = MutableStateFlow(false)
        )
    )

    AppTheme {
        Surface {
            IndividualEditContent(
                uiState = uiState,
                onFirstNameChange = {},
                onLastNameChange = {},
                onPhoneChange = {},
                onEmailChange = {},
                onBirthDateClick = {},
                onAlarmTimeClick = {},
                onIndividualTypeChange = {},
                onAvailableChange = {}
            )
        }
    }
}