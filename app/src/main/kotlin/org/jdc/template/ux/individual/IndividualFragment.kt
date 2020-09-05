package org.jdc.template.ux.individual

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.ui.compose.AppTheme
import org.jdc.template.ui.compose.setContent
import org.jdc.template.ux.main.NavControllerAmbient
import java.time.LocalDate
import java.time.Month
import java.time.OffsetDateTime

@AndroidEntryPoint
class IndividualFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return setContent {
            Providers(NavControllerAmbient provides findNavController()) {
                IndividualPage()
            }
        }
    }
}

@Composable
private fun IndividualPage() {
    val viewModel = viewModel<IndividualViewModel>()
    AppTheme {
        Scaffold(
            topBar = { AppBar() }
        ) { padding ->
            val individual = viewModel.individualFlow.collectAsState(Individual())
            IndividualView(individual.value, Modifier.padding(padding))
            if (viewModel.confirmDeleteState) {
                ConfirmDeleteDialog(viewModel)
            }
        }
    }
}

@Composable
private fun AppBar() {
    val navController = NavControllerAmbient.current
    val viewModel: IndividualViewModel = viewModel()
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack)
            }
        },
        title = {
            Text(stringResource(id = R.string.individual))
        },
        actions = {
            IconButton(onClick = {
                val directions = IndividualFragmentDirections.actionIndividualEditFragment(viewModel.individualId)
                navController.navigate(directions)
            }) {
                Icon(Icons.Filled.Edit)
            }
            IconButton(onClick = { viewModel.confirmDeleteState = true }) {
                Icon(Icons.Filled.Delete)
            }
        },
    )
}

@Composable
private fun IndividualView(individual: Individual, modifier: Modifier = Modifier) {
    ScrollableColumn(modifier.padding(16.dp)) {
        Text(text = individual.getFullName(), style = MaterialTheme.typography.h4)
        Text(text = stringResource(id = R.string.phone), modifier = Modifier.padding(top = 8.dp), style = MaterialTheme.typography.caption)
        Text(text = individual.phone, style = MaterialTheme.typography.body1)
        Text(text = stringResource(id = R.string.email), modifier = Modifier.padding(top = 8.dp), style = MaterialTheme.typography.caption)
        Text(text = individual.email, style = MaterialTheme.typography.body1)
        Text(text = stringResource(id = R.string.birth_date), modifier = Modifier.padding(top = 8.dp), style = MaterialTheme.typography.caption)
        val birthDate = individual.birthDate?.let {
            val millis1 = OffsetDateTime.now().with(individual.birthDate).toInstant().toEpochMilli()
            DateUtils.formatDateTime(ContextAmbient.current, millis1, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR)
        } ?: ""
        Text(text = birthDate, style = MaterialTheme.typography.body1)
        Text(text = stringResource(id = R.string.alarm_time), modifier = Modifier.padding(top = 8.dp), style = MaterialTheme.typography.caption)
        val millis2 = OffsetDateTime.now().with(individual.alarmTime).toInstant().toEpochMilli()
        val alarmTime = DateUtils.formatDateTime(ContextAmbient.current, millis2, DateUtils.FORMAT_SHOW_TIME)
        Text(text = alarmTime, style = MaterialTheme.typography.body1)
    }
}

@Composable
@Preview(showBackground = true)
private fun IndividualPreview() {
    val individual = Individual(firstName = "George", lastName = "Washington", birthDate = LocalDate.of(1732, Month.FEBRUARY, 22), phone = "(202) 456-1111", email = "whitehouse@us.gov")
    IndividualView(individual)
}

@Composable
private fun ConfirmDeleteDialog(viewModel: IndividualViewModel) {
    val navController = NavControllerAmbient.current
    AlertDialog(
        onDismissRequest = { viewModel.confirmDeleteState = false },
        text = { Text(stringResource(R.string.delete_individual_confirm)) },
        confirmButton = {
            Button(onClick = {
                navController.popBackStack()
                viewModel.deleteIndividual()
            }) {
                Text(stringResource(R.string.delete))
            }
        },
        dismissButton = {
            Button(onClick = { viewModel.confirmDeleteState = false }) {
                Text(stringResource(R.string.cancel))
            }
        },
    )
}
