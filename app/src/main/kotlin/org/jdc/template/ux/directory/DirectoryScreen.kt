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
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.R
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.compose.appbar.AppBarMenu
import org.jdc.template.ui.compose.appbar.AppBarMenuItem
import org.jdc.template.ui.compose.appbar.AppScaffold
import org.jdc.template.ui.navigation.HandleNavigation
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.settings.SettingsRoute

@Composable
fun DirectoryScreen(viewModel: DirectoryViewModel = hiltViewModel()) {
    val navController = LocalNavController.current

    val appBarMenuItems = listOf(
        // icons
        AppBarMenuItem.Icon(Icons.Default.Search, stringResource(R.string.search)) {},

        // overflow
        AppBarMenuItem.OverflowItem(stringResource(R.string.settings)) { navController?.navigate(SettingsRoute.createRoute()) },
        AppBarMenuItem.OverflowItem(stringResource(R.string.about)) { navController?.navigate(AboutRoute.createRoute()) },
    )

    AppScaffold(
        title = stringResource(R.string.directory),
        actions = { AppBarMenu(appBarMenuItems) }
    ) {
        DirectoryContent(
            viewModel.directoryListFlow,
            onDirectoryIndividualClicked = { viewModel.onDirectoryIndividualClicked(it) },
            onNewClicked = { viewModel.addIndividual() }
        )
    }

    HandleNavigation(viewModel, navController, viewModel.navigatorFlow)
}

@Composable
private fun DirectoryContent(
    directoryListFlow: StateFlow<List<DirectoryItem>>,
    onDirectoryIndividualClicked: (String) -> Unit,
    onNewClicked: () -> Unit,
) {
    val directoryList by directoryListFlow.collectAsState()

    Box(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(directoryList) { individual ->
                // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#listitem
                ListItem(
                    Modifier
                        .clickable { onDirectoryIndividualClicked(individual.individualId) },
                ) {
                    Text(individual.getFullName())
                }
            }
        }

        FloatingActionButton(
            onClick = { onNewClicked() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add))
        }
    }
}
