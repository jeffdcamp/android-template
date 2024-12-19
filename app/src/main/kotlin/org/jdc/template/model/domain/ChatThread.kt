package org.jdc.template.model.domain

import org.jdc.template.model.domain.inline.ChatThreadId
import org.jdc.template.model.domain.inline.IndividualId

data class ChatThread(
    val id: ChatThreadId,
    val name: String,
    val ownerIndividualId: IndividualId,
)