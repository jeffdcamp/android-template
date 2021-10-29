package org.jdc.template.ux.individual

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jdc.template.R
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.ui.DateUiUtil
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.compose.dialog.MessageDialog

@Composable
fun IndividualScreen() {
    val navController = LocalNavController.current
    val viewModel: IndividualViewModel = viewModel()

    val individual: Individual? by viewModel.individualFlow.collectAsState()
    IndividualSummary(individual)

    val messageDialogData by viewModel.messageDialogDataFlow.collectAsState()

    if (messageDialogData.visible) {
        MessageDialog(
            title = messageDialogData.title,
            text = messageDialogData.text,
            onDismissRequest = { viewModel.hideInfoDialog() },
            onConfirmButtonClicked = {
                viewModel.deleteIndividual()
                navController?.popBackStack()
            },
            onDismissButtonClicked = { viewModel.hideInfoDialog() }
        )
    }
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

//@Preview
//@Composable
//fun TestIndividual() {
//    IndividualSummary(
//        individual = Individual(
//            firstName = "Jeff",
//            lastName = "Campbell",
//            phone = "801-555-1234",
//            email = "bob@bob.com",
////        birthDate = LocalDate.MIN,
////        alarmTime = LocalTime.MIN
//        )
//    )
//}
