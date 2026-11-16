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
import org.jdc.template.ui.navigation.HandleNavigation3
import org.jdc.template.ui.navigation.navigator.Navigation3Navigator
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

        FlowTextField(
            stringResource(R.string.first_name),
            formFields.firstName,
            onFirstNameChange,
            fieldModifier.testTag(IndividualEditScreenFields.FIRST_NAME.name),
            formFields.firstNameError
        )
        FlowTextField(stringResource(R.string.last_name), formFields.lastName, onLastNameChange, fieldModifier.testTag(IndividualEditScreenFields.LAST_NAME.name))
        FlowTextField(stringResource(R.string.phone), formFields.phoneNumber, onPhoneChange, fieldModifier.testTag(IndividualEditScreenFields.PHONE.name))
        FlowTextField(stringResource(R.string.email), formFields.email, onEmailChange, fieldModifier.testTag(IndividualEditScreenFields.EMAIL.name), formFields.emailError)

        DateClickableTextField(
            label = stringResource(R.string.birth_date),
            date = formFields.birthDate,
            onClick = onBirthDateClick,
            modifier = fieldModifier.testTag(IndividualEditScreenFields.BIRTH_DATE.name),
            errorText = formFields.birthDateError
        )
        TimeClickableTextField(stringResource(R.string.alarm_time), formFields.alarmTime, onAlarmTimeClick, fieldModifier.testTag(IndividualEditScreenFields.ALARM_TIME.name))

        DropdownMenuBoxField(
            label = stringResource(R.string.individual_type),
            options = IndividualType.entries,
            selectedOption = formFields.individualType,
            onOptionSelected = { onIndividualTypeChange(it) },
            optionToText = { stringResource(it.getStringResId()) },
            supportingText = formFields.individualTypeError,
            isError = !formFields.individualTypeError.isNullOrBlank(),
            modifier = fieldModifier
                .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
                .testTag(IndividualEditScreenFields.TYPE.name)
        )

        SwitchField(stringResource(R.string.available), formFields.available, onAvailableChange, fieldModifier.testTag(IndividualEditScreenFields.AVAILABLE.name))
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
            firstName = "Jeff",
            lastName = "Campbell",
            phoneNumber = "801-555-0001",
            email = "bob@bob.com",
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