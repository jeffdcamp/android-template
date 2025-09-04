package org.jdc.template.ux.chat

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.shared.model.domain.inline.ChatMessageId
import org.jdc.template.shared.model.domain.inline.IndividualId
import org.jdc.template.shared.model.repository.ChatRepository
import org.jdc.template.shared.util.ext.stateInDefault
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.ui.compose.dialog.showMessageDialog
import org.jdc.template.ui.navigation3.ViewModelNavigation3
import org.jdc.template.ui.navigation3.ViewModelNavigation3Impl

@HiltViewModel(assistedFactory = ChatViewModel.Factory::class)
class ChatViewModel
@AssistedInject constructor(
    private val chatRepository: ChatRepository,
    @Assisted val route: ChatRoute
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)
    private val threadNameFlow = MutableStateFlow<String>("Test")
    private val fromPerspectiveUserIdFlow = MutableStateFlow<IndividualId>(route.individualId)

    private val allMessagesPagingFlow = chatRepository.getPagingAllMessagesFlow(route.chatThreadId)
        .cachedIn(viewModelScope)

    val uiState: ChatUiState = ChatUiState(
        dialogUiStateFlow = dialogUiStateFlow,
        threadNameFlow = threadNameFlow.stateInDefault(viewModelScope, ""),
        fromPerspectiveUserId = fromPerspectiveUserIdFlow.stateInDefault(viewModelScope, route.individualId),
        allMessagesPagingFlow = allMessagesPagingFlow.stateInDefault(viewModelScope, PagingData.empty()),
        onSendClick = { onMessageSend(it) },
        onDeleteClick = { onDeleteMessage(it) },
    )

    private fun onMessageSend(text: String) = viewModelScope.launch {
        chatRepository.sendMessageAsync(route.chatThreadId, route.individualId, text)
    }

    private fun onDeleteMessage(chatMessageId: ChatMessageId) {
        showMessageDialog(dialogUiStateFlow,
            text = { "Delete Message?" },
            confirmButtonText = { stringResource(R.string.delete) },
            onConfirm = { onDeleteConfirm(chatMessageId) },
            onDismiss = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun onDeleteConfirm(chatMessageId: ChatMessageId) = viewModelScope.launch {
        chatRepository.deleteMessage(chatMessageId)
    }

    @AssistedFactory
    interface Factory {
        fun create(chatRoute: ChatRoute): ChatViewModel
    }
}
