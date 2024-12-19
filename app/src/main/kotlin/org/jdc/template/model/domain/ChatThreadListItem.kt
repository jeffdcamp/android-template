package org.jdc.template.model.domain

import org.jdc.template.model.domain.inline.ChatThreadId

data class ChatThreadListItem(
    val id: ChatThreadId,
    val name: String,
)
