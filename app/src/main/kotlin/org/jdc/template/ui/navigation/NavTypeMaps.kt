package org.jdc.template.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import org.jdc.template.model.domain.inline.ChatThreadId
import org.jdc.template.model.domain.inline.IndividualId

/**
 * Mappings for type safe navigation
 *
 * Notes:
 * 1. parseValue() should know how to parse what is returned from serializeAsValue() (format doesn't really matter... doesn't need to be json)
 * 2. If value for NavType<XXX> is complex (class with multiple variables) use Json.encodeToString<XXX>(value) and Json.decodeFromString<XXX>(value)
 */
object NavTypeMaps {
    val IndividualIdNavType = object : NavType<IndividualId>(
        isNullableAllowed = false
    ) {
        override fun put(bundle: Bundle, key: String, value: IndividualId) = bundle.putString(key, serializeAsValue(value))
        override fun get(bundle: Bundle, key: String): IndividualId? = bundle.getString(key)?.let { parseValue(it) }
        override fun serializeAsValue(value: IndividualId): String = value.value
        override fun parseValue(value: String) = IndividualId(value)
    }

    val IndividualIdNullableNavType = object : NavType<IndividualId?>(
        isNullableAllowed = true
    ) {
        override fun put(bundle: Bundle, key: String, value: IndividualId?) {
            value?.let { bundle.putString(key, it.value) }
        }
        override fun get(bundle: Bundle, key: String): IndividualId? = bundle.getString(key)?.let { IndividualId(it) }

        override fun serializeAsValue(value: IndividualId?): String = value?.value.orEmpty()

        override fun parseValue(value: String): IndividualId? {
            if (value.isBlank()) return null
            return IndividualId(value)
        }
    }

    val ChatThreadIdNavType = object : NavType<ChatThreadId>(
        isNullableAllowed = false
    ) {
        override fun put(bundle: Bundle, key: String, value: ChatThreadId) = bundle.putString(key, serializeAsValue(value))
        override fun get(bundle: Bundle, key: String): ChatThreadId? = bundle.getString(key)?.let { parseValue(it) }
        override fun serializeAsValue(value: ChatThreadId): String = value.value
        override fun parseValue(value: String) = ChatThreadId(value)
    }
}