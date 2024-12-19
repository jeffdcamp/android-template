package org.jdc.template.ux.chat

import kotlinx.serialization.Serializable
import org.jdc.template.model.domain.inline.ChatThreadId
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.ui.navigation.NavTypeMaps
import org.jdc.template.ui.navigation.NavigationRoute
import kotlin.reflect.typeOf

@Serializable
data class ChatRoute(
    val chatThreadId: ChatThreadId,
    val individualId: IndividualId,
): NavigationRoute

fun ChatRoute.Companion.typeMap() = mapOf(
    typeOf<ChatThreadId>() to NavTypeMaps.ChatThreadIdNavType,
    typeOf<IndividualId>() to NavTypeMaps.IndividualIdNavType,
)
