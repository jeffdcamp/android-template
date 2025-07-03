package org.jdc.template.shared.model.db.main.chatmessage

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.Instant
import org.jdc.template.shared.model.db.main.chatthread.ChatThreadEntity
import org.jdc.template.shared.model.domain.inline.ChatMessageId
import org.jdc.template.shared.model.domain.inline.ChatThreadId
import org.jdc.template.shared.model.domain.inline.IndividualId
import kotlin.uuid.Uuid

@Entity(
    tableName = "ChatMessage",
    foreignKeys = [
        ForeignKey(
            entity = ChatThreadEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("chatThreadId"),
            deferred = true,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ChatMessageEntity(
    @PrimaryKey
    val id: ChatMessageId = ChatMessageId(Uuid.random().toString()),
    val chatThreadId: ChatThreadId,
    val individualId: IndividualId,
    val message: String,

    val createdDate: Instant = Clock.System.now(),
    val lastModified: Instant = Clock.System.now(),
)