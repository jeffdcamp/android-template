package org.jdc.template.ux.chats

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.model.domain.ChatThreadListItem
import org.jdc.template.model.domain.inline.ChatThreadId
import org.jdc.template.ui.compose.dialog.DialogUiState

data class ChatsUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val chatListFlow: StateFlow<List<ChatThreadListItem>>,

    // events
    val onThreadClick: (ChatThreadId) -> Unit,
    val onNewClick: () -> Unit
)

