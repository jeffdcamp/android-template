package org.jdc.template.ux.chat

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.shared.model.domain.ChatMessage
import org.jdc.template.shared.model.domain.inline.ChatMessageId
import org.jdc.template.shared.model.domain.inline.IndividualId
import org.jdc.template.shared.model.repository.ChatRepository
import org.jdc.template.shared.util.ext.stateInDefault
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.ui.compose.dialog.showMessageDialog
import org.jdc.template.ui.navigation3.ViewModelNavigation3
import org.jdc.template.ui.navigation3.ViewModelNavigation3Impl

class ChatViewModel(
    private val chatRepository: ChatRepository,
    val route: ChatRoute
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?>
        field = MutableStateFlow<DialogUiState<*>?>(null)

    private val threadNameFlow = MutableStateFlow("Test")
    private val fromPerspectiveUserIdFlow = MutableStateFlow(route.individualId)
    private val allMessagesPagingFlow: Flow<PagingData<ChatMessage>> = chatRepository.getPagingAllMessagesFlow(route.chatThreadId).cachedIn(viewModelScope)

    val uiState: StateFlow<ChatUiState> = combine(
        threadNameFlow,
        fromPerspectiveUserIdFlow,
    ) { threadName, fromPerspectiveUserId ->
        ChatUiState.Ready(
            threadName = threadName,
            fromPerspectiveUserId = fromPerspectiveUserId,
            allMessagesPagingFlow = allMessagesPagingFlow
        )
    }.stateInDefault(viewModelScope, ChatUiState.Loading)

    fun onMessageSend(text: String) = viewModelScope.launch {
        chatRepository.sendMessageAsync(route.chatThreadId, route.individualId, text)
    }

    fun onDeleteMessage(chatMessageId: ChatMessageId) {
        showMessageDialog(dialogUiStateFlow,
            text = { stringResource(R.string.delete_message_question) },
            confirmButtonText = { stringResource(R.string.delete) },
            onConfirm = { onDeleteConfirm(chatMessageId) },
            onDismiss = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun onDeleteConfirm(chatMessageId: ChatMessageId) = viewModelScope.launch {
        chatRepository.deleteMessage(chatMessageId)
    }
}

sealed interface ChatUiState {
    data object Loading : ChatUiState

    data class Ready(
        val threadName: String,
        val fromPerspectiveUserId: IndividualId,
        val allMessagesPagingFlow: Flow<PagingData<ChatMessage>>,
    ) : ChatUiState

    data object Empty : ChatUiState
}
