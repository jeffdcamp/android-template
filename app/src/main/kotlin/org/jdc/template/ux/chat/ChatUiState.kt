package org.jdc.template.ux.chat

import androidx.paging.PagingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.model.domain.ChatMessage
import org.jdc.template.model.domain.inline.ChatMessageId
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.ui.compose.dialog.DialogUiState

data class ChatUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val threadNameFlow: StateFlow<String>,
    val fromPerspectiveUserId: StateFlow<IndividualId>,
    val allMessagesPagingFlow: StateFlow<PagingData<ChatMessage>>,

    // events
    val onSendClick: (String) -> Unit,
    val onDeleteClick: (ChatMessageId) -> Unit,
)
