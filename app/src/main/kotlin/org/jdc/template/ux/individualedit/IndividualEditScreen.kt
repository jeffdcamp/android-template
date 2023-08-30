@file:Suppress("MatchingDeclarationName")
package org.jdc.template.ux.individualedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
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
import org.jdc.template.ui.compose.form.SwitchField
import org.jdc.template.ui.compose.form.TextFieldData
import org.jdc.template.ui.compose.form.TextFieldDataTextField
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
        AppBarMenuItem.Text({ stringResource(R.string.save) }) { uiState.saveIndividual() },
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.edit_individual),
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStack() },
        hideNavigation = true
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
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .imePadding()
    ) {
        val focusManager = LocalFocusManager.current

        val fieldModifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)

        TextFieldDataTextField(stringResource(R.string.first_name), uiState.firstNameFlow, "firstNameEditTextTag", uiState.firstNameOnChange, fieldModifier)
        TextFieldDataTextField(stringResource(R.string.last_name), uiState.lastNameFlow, "lastNameEditTextTag", uiState.lastNameOnChange, fieldModifier)
        TextFieldDataTextField(stringResource(R.string.phone), uiState.phoneFlow, "phoneEditTextTag", uiState.phoneOnChange, fieldModifier)
        TextFieldDataTextField(stringResource(R.string.email), uiState.emailFlow, "emailEditTextTag", uiState.emailOnChange, fieldModifier)

        DateClickableTextField(stringResource(R.string.birth_date), uiState.birthDateFlow, uiState.birthDateClicked, fieldModifier)
        TimeClickableTextField(stringResource(R.string.alarm_time), uiState.alarmTimeFlow, uiState.alarmTimeClicked, fieldModifier)

        DropdownMenuBoxField(
            label = stringResource(R.string.individual_type),
            options = IndividualType.values().asList(),
            selectedOptionFlow = uiState.individualTypeFlow,
            onOptionSelected = { uiState.individualTypeChange(it) },
            optionToText = { stringResource(it.textResId) },
            modifier = fieldModifier
                .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
                .testTag("individualTypeTextTag")
        )

        SwitchField(stringResource(R.string.available), uiState.availableFlow, uiState.availableOnChange, fieldModifier)
    }
}

@PreviewDefault
@Composable
private fun Preview() {
    val uiState = IndividualEditUiState(
        firstNameFlow = MutableStateFlow(TextFieldData("Jeff"))
    )

    AppTheme {
        Surface {
            IndividualEditFields(uiState)
        }
    }
}