@file:Suppress("MatchingDeclarationName")
package org.jdc.template.ux.individualedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import org.jdc.template.R
import org.jdc.template.model.domain.type.IndividualType
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
import org.jdc.template.ui.navigation.HandleNavigation
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun IndividualEditScreen(
    navController: NavController,
    viewModel: IndividualEditViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    val appBarMenuItems = listOf(
        AppBarMenuItem.Text({ stringResource(R.string.save) }) { uiState.onSaveIndividualClick() },
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.edit_individual),
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStack() },
        hideNavigation = true
    ) {
        IndividualEditContent(viewModel.uiState)
    }

    HandleDialogUiState(uiState.dialogUiStateFlow)

    HandleNavigation(viewModel, navController)
}

@Composable
fun IndividualEditContent(
    uiState: IndividualEditUiState
) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        val focusManager = LocalFocusManager.current

        val fieldModifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)

        FlowTextField(stringResource(R.string.first_name), uiState.firstNameFlow, uiState.firstNameOnChange, fieldModifier.testTag(IndividualEditScreenFields.FIRST_NAME.name),
            uiState.firstNameErrorFlow)
        FlowTextField(stringResource(R.string.last_name), uiState.lastNameFlow, uiState.lastNameOnChange, fieldModifier.testTag(IndividualEditScreenFields.LAST_NAME.name))
        FlowTextField(stringResource(R.string.phone), uiState.phoneFlow, uiState.phoneOnChange, fieldModifier.testTag(IndividualEditScreenFields.PHONE.name))
        FlowTextField(stringResource(R.string.email), uiState.emailFlow, uiState.emailOnChange, fieldModifier.testTag(IndividualEditScreenFields.EMAIL.name), uiState.emailErrorFlow)

        DateClickableTextField(
            stringResource(R.string.birth_date),
            uiState.birthDateFlow,
            uiState.birthDateClick,
            fieldModifier.testTag(IndividualEditScreenFields.BIRTH_DATE.name),
            uiState.birthDateErrorFlow
        )
        TimeClickableTextField(stringResource(R.string.alarm_time), uiState.alarmTimeFlow, uiState.alarmTimeClick, fieldModifier.testTag(IndividualEditScreenFields.ALARM_TIME.name))

        DropdownMenuBoxField(
            label = stringResource(R.string.individual_type),
            options = IndividualType.entries,
            selectedOptionFlow = uiState.individualTypeFlow,
            onOptionSelected = { uiState.individualTypeChange(it) },
            optionToText = { stringResource(it.textResId) },
            errorTextFlow = uiState.individualTypeErrorFlow,
            modifier = fieldModifier
                .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
                .testTag(IndividualEditScreenFields.TYPE.name)
        )

        SwitchField(stringResource(R.string.available), uiState.availableFlow, uiState.availableOnChange, fieldModifier.testTag(IndividualEditScreenFields.AVAILABLE.name))
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
    val uiState = IndividualEditUiState(
        firstNameFlow = MutableStateFlow("Jeff")
    )

    AppTheme {
        Surface {
            IndividualEditContent(uiState)
        }
    }
}