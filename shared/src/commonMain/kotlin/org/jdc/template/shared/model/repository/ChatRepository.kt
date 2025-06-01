package org.jdc.template.shared.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import org.jdc.template.shared.model.db.main.MainDatabase
import org.jdc.template.shared.model.db.main.chatmessage.ChatMessageEntity
import org.jdc.template.shared.model.db.main.chatthread.ChatThreadEntity
import org.jdc.template.shared.model.domain.ChatMessage
import org.jdc.template.shared.model.domain.ChatThread
import org.jdc.template.shared.model.domain.ChatThreadListItem
import org.jdc.template.shared.model.domain.inline.ChatMessageId
import org.jdc.template.shared.model.domain.inline.ChatThreadId
import org.jdc.template.shared.model.domain.inline.IndividualId

class ChatRepository(
    private val mainDatabase: MainDatabase,
    private val ioDispatcher: CoroutineDispatcher,
    private val appScope: CoroutineScope,
) {
    private fun chatThreadDao() = mainDatabase.chatThreadDao()
    private fun chatMessageDao() = mainDatabase.chatMessageDao()

    fun getChatThreadListFlow(): Flow<List<ChatThreadListItem>> = chatThreadDao().findAllChatThreadListItemFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPagingAllMessagesFlow(chatThreadId: ChatThreadId): Flow<PagingData<ChatMessage>> {
        val config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = false)

        return Pager(config) {
            chatMessageDao().findAllPaging(chatThreadId)
        }.flow.mapLatest { page: PagingData<ChatMessageEntity> ->
            page.map { entity ->
                entity.toChatMessage()
            }
        }
    }

    fun sendMessageAsync(chatThreadId: ChatThreadId, individualId: IndividualId, message: String) = appScope.launch(ioDispatcher) {
        val newMessage = ChatMessageEntity(
            chatThreadId = chatThreadId,
            individualId = individualId,
            message = message
        )

        chatMessageDao().insert(newMessage)
    }

    suspend fun deleteMessage(chatMessageId: ChatMessageId) {
        chatMessageDao().deleteById(chatMessageId)
    }

    suspend fun getChatThreadById(chatThreadId: ChatThreadId): ChatThread? = chatThreadDao().findById(chatThreadId)?.toChatThread()
    suspend fun saveNewChatThread(chatThread: ChatThread) {
        chatThreadDao().insert(chatThread.toEntity())
    }
}

private fun ChatMessageEntity.toChatMessage() = ChatMessage(
    id = id,
    chatThreadId = chatThreadId,
    individualId = individualId,
    message = message,
    createdDate = createdDate,
    lastModified = lastModified
)

private fun ChatThreadEntity.toChatThread() = ChatThread(
    id = id,
    name = name,
    ownerIndividualId = ownerIndividualId
)

private fun ChatThread.toEntity() = ChatThreadEntity(
    id = id,
    name = name,
    ownerIndividualId = ownerIndividualId
)
