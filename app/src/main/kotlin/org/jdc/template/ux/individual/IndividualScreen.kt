package org.jdc.template.ux.individual

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.jdc.template.R
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.ui.DateUiUtil
import org.jdc.template.ui.compose.appbar.AppBarMenu
import org.jdc.template.ui.compose.appbar.AppBarMenuItem
import org.jdc.template.ui.compose.dialog.HandleDialog
import org.jdc.template.ui.compose.dialog.MessageDialog
import org.jdc.template.ui.navigation.HandleNavigation
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun IndividualScreen(
    navController: NavController,
    viewModel: IndividualViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    val appBarMenuItems = listOf(
        AppBarMenuItem.Icon(Icons.Default.Edit, stringResource(R.string.edit)) { uiState.onEdit() },
        AppBarMenuItem.Icon(Icons.Default.Delete, stringResource(R.string.delete)) { uiState.onDelete() }
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.individual),
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStack() },
    ) {
        IndividualContent(uiState)
    }

    HandleDialog(uiState.deleteIndividualDialogDataFlow) {
        MessageDialog(
            title = it.title,
            text = it.text,
            onConfirmButtonClicked = { uiState.deleteIndividual() },
            onDismissButtonClicked = { uiState.dismissDeleteIndividualDialog() },
            onDismissRequest = { uiState.dismissDeleteIndividualDialog() }
        )
    }

    HandleNavigation(viewModel, navController)
}

@Composable
private fun IndividualContent(uiState: IndividualUiState) {
    val individual by uiState.individualFlow.collectAsState()
    individual?.let { IndividualSummary(it) }
}

@Composable
private fun IndividualSummary(individual: Individual) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        IndividualSummaryItem(individual.getFullName(), textStyle = MaterialTheme.typography.h5)
        IndividualSummaryItem(individual.phone, stringResource(R.string.phone))
        IndividualSummaryItem(individual.email, stringResource(R.string.email))
        IndividualSummaryItem(DateUiUtil.getLocalDateText(LocalContext.current, individual.birthDate), stringResource(R.string.birth_date))
        IndividualSummaryItem(DateUiUtil.getLocalTimeText(LocalContext.current, individual.alarmTime), stringResource(R.string.alarm_time))
    }
}

@Composable
private fun IndividualSummaryItem(
    text: String?,
    label: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.body1
) {
    if (text.isNullOrBlank()) {
        return
    }
    Column {
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
            )
        }
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier
                .padding(start = 16.dp, top = if (label != null) 4.dp else 16.dp)
        )
    }
}

@Preview(group = "light", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(group = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Composable
fun PreviewIndividual() {
    AppTheme {
        IndividualSummary(
            individual = Individual(
                firstName = "Jeff",
                lastName = "Campbell",
                phone = "801-555-0001",
                email = "bob@bob.com",
//        birthDate = LocalDate.MIN,
//        alarmTime = LocalTime.MIN
            )
        )
    }
}
