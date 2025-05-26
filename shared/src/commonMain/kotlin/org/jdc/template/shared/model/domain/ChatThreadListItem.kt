package org.jdc.template.shared.model.domain

import org.jdc.template.shared.model.domain.inline.ChatThreadId

data class ChatThreadListItem(
    val id: ChatThreadId,
    val name: String,
)
