package org.jdc.template.ux.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jdc.template.shared.model.domain.ChatThread
import org.jdc.template.shared.model.domain.ChatThreadListItem
import org.jdc.template.shared.model.domain.inline.ChatThreadId
import org.jdc.template.shared.model.repository.ChatRepository
import org.jdc.template.shared.model.repository.IndividualRepository
import org.jdc.template.shared.util.ext.stateInDefault
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.navigation.ViewModelNavigation3
import org.jdc.template.ui.navigation.ViewModelNavigation3Impl
import org.jdc.template.ux.chat.ChatRoute
import kotlin.uuid.Uuid

class ChatsViewModel(
    private val individualRepository: IndividualRepository,
    private val chatMessageRepository: ChatRepository,
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?>
        field = MutableStateFlow<DialogUiState<*>?>(null)

    val uiStateFlow: StateFlow<ChatsUiState> = chatMessageRepository.getChatThreadListFlow().map { list: List<ChatThreadListItem> ->
        if (list.isNotEmpty()) {
            ChatsUiState.Ready(list)
        } else {
            ChatsUiState.Empty
        }
    }.stateInDefault(viewModelScope, ChatsUiState.Loading)

    fun onNewChatClick() = viewModelScope.launch {
        val allIndividuals = individualRepository.getAllIndividuals()

        if (allIndividuals.size < 2) {
            Logger.e { "Not enough individuals to start a chat" }
            return@launch
        }

        // Create a new thread
        val individual1 = allIndividuals.first()
        val individual2 = allIndividuals.last()

        val chatThread = ChatThread(
            id = ChatThreadId(Uuid.random().toString()),
            name = "${individual1.firstName?.value.orEmpty()} & ${individual2.firstName?.value.orEmpty()}",
            ownerIndividualId = individual1.id
        )

        chatMessageRepository.saveNewChatThread(chatThread)

        // navigate to the new thread
        navigate(ChatRoute(chatThread.id, chatThread.ownerIndividualId))
    }

    fun onChatThreadClick(chatThreadId: ChatThreadId) = viewModelScope.launch {
        val chatThread = chatMessageRepository.getChatThreadById(chatThreadId) ?: return@launch
        navigate(ChatRoute(chatThread.id, chatThread.ownerIndividualId))
    }
}

sealed interface ChatsUiState {
    data object Loading : ChatsUiState

    data class Ready(
        val threadsList: List<ChatThreadListItem>,
    ) : ChatsUiState

    data object Empty : ChatsUiState
}
