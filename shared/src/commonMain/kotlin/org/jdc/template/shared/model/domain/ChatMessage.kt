package org.jdc.template.shared.model.domain

import org.jdc.template.shared.model.domain.inline.ChatMessageId
import org.jdc.template.shared.model.domain.inline.ChatThreadId
import org.jdc.template.shared.model.domain.inline.IndividualId
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.Uuid

data class ChatMessage(
    val id: ChatMessageId = ChatMessageId(Uuid.random().toString()),
    val chatThreadId: ChatThreadId,
    val individualId: IndividualId,
    val message: String,

    val createdDate: Instant = Clock.System.now(),
    val lastModified: Instant = Clock.System.now(),
)
