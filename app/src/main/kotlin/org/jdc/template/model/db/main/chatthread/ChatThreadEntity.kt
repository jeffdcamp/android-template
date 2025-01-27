package org.jdc.template.model.db.main.chatthread

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jdc.template.model.domain.inline.ChatThreadId
import org.jdc.template.model.domain.inline.IndividualId
import java.util.UUID

@Entity(tableName = "ChatThread")
data class ChatThreadEntity(
    @PrimaryKey
    val id: ChatThreadId = ChatThreadId(UUID.randomUUID().toString()),
    val name: String,
    val ownerIndividualId: IndividualId,

    val createdDate: Instant = Clock.System.now(),
    val lastModified: Instant = Clock.System.now(),
)