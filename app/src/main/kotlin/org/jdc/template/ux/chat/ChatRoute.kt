package org.jdc.template.ux.chat

import androidx.navigation3.runtime.NavKey
import org.jdc.template.shared.model.domain.inline.ChatThreadId
import org.jdc.template.shared.model.domain.inline.IndividualId

data class ChatRoute(
    val chatThreadId: ChatThreadId,
    val individualId: IndividualId,
): NavKey
