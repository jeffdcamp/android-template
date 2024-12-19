package org.jdc.template.ux.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jdc.template.model.domain.ChatThread
import org.jdc.template.model.domain.inline.ChatThreadId
import org.jdc.template.model.repository.ChatRepository
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.navigation.ViewModelNavigation
import org.jdc.template.ui.navigation.ViewModelNavigationImpl
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.ux.chat.ChatRoute
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel
@Inject constructor(
    private val individualRepository: IndividualRepository,
    private val chatMessageRepository: ChatRepository,
) : ViewModel(), ViewModelNavigation by ViewModelNavigationImpl() {

    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)

    val uiState: ChatsUiState = ChatsUiState(
        dialogUiStateFlow = dialogUiStateFlow,
        chatListFlow = chatMessageRepository.getChatThreadListFlow().stateInDefault(viewModelScope, emptyList()),
        onThreadClick = { onChatThreadClick(it) },
        onNewClick = { onNewChatClick() },
    )

    private fun onNewChatClick() = viewModelScope.launch {
        val allIndividuals = individualRepository.getAllIndividuals()

        if (allIndividuals.size < 2) {
            Logger.e { "Not enough individuals to start a chat" }
            return@launch
        }

        // Create a new thread
        val individual1 = allIndividuals.first()
        val individual2 = allIndividuals.last()

        val chatThread = ChatThread(
            id = ChatThreadId(UUID.randomUUID().toString()),
            name = "${individual1.firstName?.value.orEmpty()} & ${individual2.firstName?.value.orEmpty()}",
            ownerIndividualId = individual1.id
        )

        chatMessageRepository.saveNewChatThread(chatThread)

        // navigate to the new thread
        navigate(ChatRoute(chatThread.id, chatThread.ownerIndividualId))
    }

    private fun onChatThreadClick(chatThreadId: ChatThreadId) = viewModelScope.launch {
        val chatThread = chatMessageRepository.getChatThreadById(chatThreadId) ?: return@launch
        navigate(ChatRoute(chatThread.id, chatThread.ownerIndividualId))
    }
}
