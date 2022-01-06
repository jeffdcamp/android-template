package org.jdc.template.ux.directory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.jdc.template.ui.navigation.HandleNavigation
import org.jdc.template.ui.compose.LocalNavController

@Composable
fun DirectoryScreen(viewModel: DirectoryViewModel = hiltViewModel()) {
    val navController = LocalNavController.current

    val directoryList by viewModel.directoryListFlow.collectAsState()

    Box(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(directoryList) { individual ->
                // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#listitem
                ListItem(
                    Modifier
                        .clickable { viewModel.onDirectoryIndividualClicked(individual) },
                )
                { Text(individual.getFullName()) }

            }
        }

        FloatingActionButton(
            onClick = { viewModel.addIndividual() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }

    HandleNavigation(viewModel, navController, viewModel.navigateRouteFlow)
}