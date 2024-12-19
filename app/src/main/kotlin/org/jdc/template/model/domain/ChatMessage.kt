package org.jdc.template.model.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jdc.template.model.domain.inline.ChatMessageId
import org.jdc.template.model.domain.inline.ChatThreadId
import org.jdc.template.model.domain.inline.IndividualId
import java.util.UUID

data class ChatMessage(
    val id: ChatMessageId = ChatMessageId(UUID.randomUUID().toString()),
    val chatThreadId: ChatThreadId,
    val individualId: IndividualId,
    val message: String,

    val createdDate: Instant = Clock.System.now(),
    val lastModified: Instant = Clock.System.now(),
)
