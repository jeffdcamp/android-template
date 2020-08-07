package org.jdc.template.ux.individual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.ui.compose.AppTheme
import org.jdc.template.ui.compose.setContent
import org.jdc.template.ui.fragment.BaseFragment

@AndroidEntryPoint
class IndividualFragment : BaseFragment() {
    private val viewModel: IndividualViewModel by viewModels()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return setContent {
            IndividualPage(viewModel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        // Events
        lifecycleScope.launch {
            for (event in viewModel.eventChannel) {
                when (event) {
                    is IndividualViewModel.Event.EditIndividualEvent -> {
                        val directions = IndividualFragmentDirections.editIndividual(event.individualId)
                        findNavController().navigate(directions)
                    }
                    is IndividualViewModel.Event.IndividualDeletedEvent -> findNavController().popBackStack()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.individual_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_edit -> {
                viewModel.editIndividual()
                true
            }
            R.id.menu_item_delete -> {
                promptDeleteIndividual()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun promptDeleteIndividual() {
        MaterialAlertDialogBuilder(requireActivity())
            .setMessage(R.string.delete_individual_confirm)
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteIndividual()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .show()
    }
}

@Composable
fun IndividualPage(viewModel: IndividualViewModel) {
    val individual by viewModel.individual.observeAsState(Individual())
    AppTheme {
        ScrollableColumn(children = {
            Column(Modifier.padding(16.dp)) {
                Text(text = individual.getFullName(), style = MaterialTheme.typography.h4)
                Text(text = stringResource(id = R.string.phone), modifier = Modifier.padding(top = 8.dp), style = MaterialTheme.typography.caption)
                Text(text = individual.phone, style = MaterialTheme.typography.body1)
                Text(text = stringResource(id = R.string.email), modifier = Modifier.padding(top = 8.dp), style = MaterialTheme.typography.caption)
                Text(text = individual.email, style = MaterialTheme.typography.body1)
                Text(text = stringResource(id = R.string.birth_date), modifier = Modifier.padding(top = 8.dp), style = MaterialTheme.typography.caption)
                Text(text = individual.birthDate?.toString() ?: "", style = MaterialTheme.typography.body1)
                Text(text = stringResource(id = R.string.alarm_time), modifier = Modifier.padding(top = 8.dp), style = MaterialTheme.typography.caption)
                Text(text = individual.alarmTime.toString(), style = MaterialTheme.typography.body1)
            }
        })
    }
}
