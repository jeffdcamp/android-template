package org.jdc.template.shared.model.db.main.chatthread

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.Instant
import org.jdc.template.shared.model.domain.inline.ChatThreadId
import org.jdc.template.shared.model.domain.inline.IndividualId
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