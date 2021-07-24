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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jdc.template.R
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.ui.DateUiUtil

@Composable
fun IndividualScreen() {
    val viewModel: IndividualViewModel = viewModel()

    val lifecycleOwner = LocalLifecycleOwner.current
    val individualFlow = viewModel.individualFlow
    val lifecycleListFlow = remember(individualFlow, lifecycleOwner) {
        individualFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val individual by lifecycleListFlow.collectAsState(Individual())
    IndividualSummary(individual = individual)
}

@Composable
fun IndividualSummary(individual: Individual) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        IndividualSummaryItem(individual.getFullName(), textStyle = MaterialTheme.typography.h5)
        IndividualSummaryItem(individual.phone, stringResource(R.string.phone))
        IndividualSummaryItem(individual.email, stringResource(R.string.email))
        IndividualSummaryItem(DateUiUtil.getLocalDateText(LocalContext.current, individual.birthDate), stringResource(R.string.birth_date))
        IndividualSummaryItem(DateUiUtil.getLocalTimeText(LocalContext.current, individual.alarmTime), stringResource(R.string.alarm_time))
    }
}

@Composable
fun IndividualSummaryItem(text: String, label: String? = null, textStyle: TextStyle = MaterialTheme.typography.body1) {//@Preview
    if (text.isBlank()) {
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

@Preview
@Composable
fun TestIndividual() {
    IndividualSummary(individual = Individual(
        firstName = "Jeff",
        lastName = "Campbell",
        phone = "801-555-1234",
        email = "bob@bob.com",
//        birthDate = LocalDate.MIN,
//        alarmTime = LocalTime.MIN
    ))
}
