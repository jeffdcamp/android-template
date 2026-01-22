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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jdc.template.R
import org.jdc.template.shared.model.domain.ChatThreadListItem
import org.jdc.template.shared.model.domain.inline.ChatThreadId
import org.jdc.template.ui.navigation3.HandleNavigation3
import org.jdc.template.ui.navigation3.navigator.Navigation3Navigator
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun ChatsScreen(
    navigator: Navigation3Navigator,
    viewModel: ChatsViewModel,
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = stringResource(R.string.chats),
        navigationIconVisible = false,
        onNavigationClick = { navigator.pop() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onNewChatClick() },
                content = { Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add)) }
            )
        }
    ) {
        when (val uiState = uiState) {
            ChatsUiState.Loading -> {}
            is ChatsUiState.Ready -> {
                ChatsContent(
                    threadsList = uiState.threadsList,
                    onThreadClick = { viewModel.onChatThreadClick(it) }
                )
            }
            ChatsUiState.Empty -> {}
        }
    }

    HandleNavigation3(viewModel, navigator)
}

@Composable
private fun ChatsContent(
    threadsList: List<ChatThreadListItem>,
    onThreadClick: (ChatThreadId) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(threadsList) { listItem ->
            ListItem(
                headlineContent = { Text(listItem.name) },
                Modifier
                    .clickable { onThreadClick(listItem.id) },
            )
        }
    }
}
