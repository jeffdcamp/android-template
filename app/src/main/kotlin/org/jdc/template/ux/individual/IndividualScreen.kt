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
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.R
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.ui.DateUiUtil
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.compose.appbar.AppBarMenu
import org.jdc.template.ui.compose.appbar.AppBarMenuItem
import org.jdc.template.ui.compose.appbar.AppScaffold
import org.jdc.template.ui.compose.dialog.MessageDialog
import org.jdc.template.ui.compose.dialog.MessageDialogData
import org.jdc.template.ui.navigation.HandleNavigation
import org.jdc.template.ui.theme.AppTheme

@Composable
fun IndividualScreen(viewModel: IndividualViewModel = hiltViewModel()) {
    val navController = LocalNavController.current

    val appBarMenuItems = listOf(
        AppBarMenuItem.Icon(Icons.Default.Edit, stringResource(R.string.edit)) { viewModel.editIndividual() },
        AppBarMenuItem.Icon(Icons.Default.Delete, stringResource(R.string.delete)) { viewModel.onDeleteClicked() }
    )

    AppScaffold(
        title = stringResource(R.string.individual),
        actions = { AppBarMenu(appBarMenuItems) }
    ) {
        IndividualContent(viewModel.individualFlow)
    }

    ShowMessageDialog(
        messageDialogDataFlow = viewModel.messageDialogDataFlow,
        onConfirmButtonClicked = {
            viewModel.deleteIndividual()
            navController?.popBackStack()
        },
        onDismissRequest = { viewModel.hideInfoDialog() }
    )

    HandleNavigation(viewModel, navController, viewModel.navigatorFlow)
}

@Composable
private fun IndividualContent(individualFlow: StateFlow<Individual?>) {
    val individual: Individual? by individualFlow.collectAsState()
    IndividualSummary(individual)
}

@Composable
private fun IndividualSummary(individual: Individual?) {
    individual ?: return

    Column(Modifier.verticalScroll(rememberScrollState())) {
        IndividualSummaryItem(individual.getFullName(), textStyle = MaterialTheme.typography.h5)
        IndividualSummaryItem(individual.phone, stringResource(R.string.phone))
        IndividualSummaryItem(individual.email, stringResource(R.string.email))
        IndividualSummaryItem(DateUiUtil.getLocalDateText(LocalContext.current, individual.birthDate), stringResource(R.string.birth_date))
        IndividualSummaryItem(DateUiUtil.getLocalTimeText(LocalContext.current, individual.alarmTime), stringResource(R.string.alarm_time))
    }
}

@Composable
private fun IndividualSummaryItem(text: String?, label: String? = null, textStyle: TextStyle = MaterialTheme.typography.body1) {
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

@Composable
private fun ShowMessageDialog(
    messageDialogDataFlow: StateFlow<MessageDialogData>,
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: () -> Unit
) {
    val messageDialogData by messageDialogDataFlow.collectAsState()

    if (messageDialogData.visible) {
        MessageDialog(
            title = messageDialogData.title,
            text = messageDialogData.text,
            onDismissRequest = { onDismissRequest() },
            onConfirmButtonClicked = {
                onConfirmButtonClicked()
            },
            onDismissButtonClicked = { onDismissRequest() }
        )
    }
}

@Preview(group = "light", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(group = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Composable
fun TestIndividual() {
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
