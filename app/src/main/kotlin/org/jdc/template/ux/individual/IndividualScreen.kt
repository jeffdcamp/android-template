package org.jdc.template.ux.individual

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.jdc.template.R
import org.jdc.template.model.domain.Individual
import org.jdc.template.model.domain.inline.Email
import org.jdc.template.model.domain.inline.FirstName
import org.jdc.template.model.domain.inline.LastName
import org.jdc.template.model.domain.inline.Phone
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.compose.appbar.AppBarMenu
import org.jdc.template.ui.compose.appbar.AppBarMenuItem
import org.jdc.template.ui.compose.dialog.HandleDialogUiState
import org.jdc.template.ui.compose.form.TextWithTitle
import org.jdc.template.ui.compose.util.DateUiUtil
import org.jdc.template.ui.navigation.HandleNavigation
import org.jdc.template.ui.navigation.popBackStackOrFinishActivity
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun IndividualScreen(
    navController: NavController,
    viewModel: IndividualViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState

    val appBarMenuItems = listOf(
        AppBarMenuItem.Icon(Icons.Outlined.Edit, { stringResource(R.string.edit) }) { uiState.onEditClick() },
        AppBarMenuItem.Icon(Icons.Outlined.Delete, { stringResource(R.string.delete) }) { uiState.onDeleteClick() }
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.individual),
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStackOrFinishActivity(context) },
    ) {
        IndividualContent(uiState)
    }

    HandleDialogUiState(uiState.dialogUiStateFlow)

    HandleNavigation(viewModel, navController)
}

@Composable
private fun IndividualContent(uiState: IndividualUiState) {
    val individual by uiState.individualFlow.collectAsStateWithLifecycle()
    individual?.let { IndividualSummary(it) }
}

@Composable
private fun IndividualSummary(individual: Individual) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp)
    ) {
        TextWithTitle(individual.getFullName(), textStyle = MaterialTheme.typography.headlineSmall)
        TextWithTitle(individual.phone?.value, stringResource(R.string.phone))
        TextWithTitle(individual.email?.value, stringResource(R.string.email))
        TextWithTitle(DateUiUtil.getLocalDateText(LocalContext.current, individual.birthDate), stringResource(R.string.birth_date))
        TextWithTitle(DateUiUtil.getLocalTimeText(LocalContext.current, individual.alarmTime), stringResource(R.string.alarm_time))
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
    AppTheme {
        Surface {
            IndividualSummary(
                individual = Individual(
                    firstName = FirstName("Jeff"),
                    lastName = LastName("Campbell"),
                    phone = Phone("801-555-0001"),
                    email = Email("bob@bob.com")
                )
            )
        }
    }
}
