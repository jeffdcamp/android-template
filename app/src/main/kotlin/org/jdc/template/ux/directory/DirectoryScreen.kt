package org.jdc.template.ux.directory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import org.jdc.template.R
import org.jdc.template.ui.compose.appbar.AppBarMenu
import org.jdc.template.ui.compose.appbar.AppBarMenuItem
import org.jdc.template.ui.navigation3.HandleNavigation3
import org.jdc.template.ui.navigation3.navigator.Navigation3Navigator
import org.jdc.template.ux.DefaultNav3FloatingActionButton
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun DirectoryScreen(
    navigator: Navigation3Navigator<NavKey>,
    viewModel: DirectoryViewModel,
) {
    val uiState = viewModel.uiState

    val appBarMenuItems = listOf(
        // icons
        AppBarMenuItem.Icon(Icons.Outlined.Search, R.string.search) {},

        // overflow
        AppBarMenuItem.OverflowMenuItem(R.string.settings) { uiState.onSettingsClick() }
    )

    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = stringResource(R.string.directory),
        navigationIconVisible = false,
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navigator.pop() },
        floatingActionButton = { navSuiteType, isWideNavRailCollapsedType, railExpanded ->
            DefaultNav3FloatingActionButton(
                icon = { Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add)) },
                onClick = { uiState.onNewClick() },
                navSuiteType = navSuiteType,
                isWideNavRailCollapsedType = isWideNavRailCollapsedType,
                railExpanded = railExpanded
            )
        }
    ) {
        DirectoryContent(
            uiState,
        )
    }

    HandleNavigation3(viewModel, navigator)
}

@Composable
private fun DirectoryContent(
    uiState: DirectoryUiState
) {
    val directoryList by uiState.directoryListFlow.collectAsStateWithLifecycle()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(directoryList) { individual ->
            ListItem(
                headlineContent = { Text(individual.getFullName()) },
                Modifier
                    .clickable { uiState.onIndividualClick(individual.individualId) },
            )
        }
    }
}
