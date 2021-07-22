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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DirectoryScreen() {
    val viewModel: DirectoryViewModel = viewModel()

    val lifecycleOwner = LocalLifecycleOwner.current
    val directoryListFlow = viewModel.directoryListFlow
    val lifecycleListFlow = remember(directoryListFlow, lifecycleOwner) {
        directoryListFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    Box(Modifier.fillMaxSize()) {
        val list by lifecycleListFlow.collectAsState(initial = emptyList())

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(list) { individual ->
                RowItem(individual.getFullName()) {
                    viewModel.onDirectoryIndividualClicked(individual)
                }
            }
        }

        FloatingActionButton(
            onClick = { viewModel.addIndividual() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun RowItem(name: String, onClick: () -> Unit) {
    Text(
        text = name,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(24.dp)
    )
}