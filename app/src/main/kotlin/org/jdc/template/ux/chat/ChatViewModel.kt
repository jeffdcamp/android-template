package org.jdc.template.ux.chat

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.model.domain.inline.ChatMessageId
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.model.repository.ChatRepository
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.ui.compose.dialog.showMessageDialog
import org.jdc.template.ui.navigation.ViewModelNavigation
import org.jdc.template.ui.navigation.ViewModelNavigationImpl
import org.jdc.template.util.ext.stateInDefault
import javax.inject.Inject

@HiltViewModel
class ChatViewModel
@Inject constructor(
    private val chatRepository: ChatRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), ViewModelNavigation by ViewModelNavigationImpl() {
    private val route: ChatRoute = savedStateHandle.toRoute(ChatRoute.typeMap())

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
}
