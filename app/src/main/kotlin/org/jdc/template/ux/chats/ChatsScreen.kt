package org.jdc.template.ux.chats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.jdc.template.R
import org.jdc.template.ui.navigation.HandleNavigation
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun ChatsScreen(
    navController: NavController,
    viewModel: ChatsViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    MainAppScaffoldWithNavBar(
        title = stringResource(R.string.chats),
        navigationIconVisible = false,
        onNavigationClick = { navController.popBackStack() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { uiState.onNewClick() },
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add))
            }
        }
    ) {
        ChatsContent(
            uiState,
        )
    }

    HandleNavigation(viewModel, navController)
}

@Composable
private fun ChatsContent(
    uiState: ChatsUiState
) {
    val threadsList by uiState.chatListFlow.collectAsStateWithLifecycle()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(threadsList) { listItem ->
            ListItem(
                headlineContent = { Text(listItem.name) },
                Modifier
                    .clickable { uiState.onThreadClick(listItem.id) },
            )
        }
    }
}
