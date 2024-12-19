package org.jdc.template.model.db.main.chatmessage

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jdc.template.model.db.main.chatthread.ChatThreadEntity
import org.jdc.template.model.domain.inline.ChatMessageId
import org.jdc.template.model.domain.inline.ChatThreadId
import org.jdc.template.model.domain.inline.IndividualId
import java.util.UUID

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
    val id: ChatMessageId = ChatMessageId(UUID.randomUUID().toString()),
    val chatThreadId: ChatThreadId,
    val individualId: IndividualId,
    val message: String,

    val createdDate: Instant = Clock.System.now(),
    val lastModified: Instant = Clock.System.now(),
)