package org.jdc.template.util.ext

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import org.jdc.template.model.domain.inline.IndividualId
import java.net.URLDecoder

@VisibleForTesting
fun createSaveStateErrorMessage(key: String) = "Missing SavedState value for Key: $key"

fun SavedStateHandle.requireString(key: String): String = requireNotNull(get<String>(key)) { createSaveStateErrorMessage(key) }
fun SavedStateHandle.requireInt(key: String): Int = requireNotNull(get<Int>(key)) { createSaveStateErrorMessage(key) }
fun SavedStateHandle.requireLong(key: String): Long = requireNotNull(get<Long>(key)) { createSaveStateErrorMessage(key) }
fun SavedStateHandle.requireFloat(key: String): Float = requireNotNull(get<Float>(key)) { createSaveStateErrorMessage(key) }
fun SavedStateHandle.requireBoolean(key: String): Boolean = requireNotNull(get<Boolean>(key)) { createSaveStateErrorMessage(key) }

fun SavedStateHandle.getString(key: String): String? = get<String>(key)
fun SavedStateHandle.getInt(key: String): Int? = get<Int>(key)
fun SavedStateHandle.getLong(key: String): Long? = get<Long>(key)
fun SavedStateHandle.getFloat(key: String): Float? = get<Float>(key)
fun SavedStateHandle.getBoolean(key: String): Boolean? = get<Boolean>(key)

inline fun <KEY, T> SavedStateHandle.getValueClass(key: String, mapToValueClass: (KEY) -> T?): T? = get<KEY>(key)?.let { mapToValueClass(it) }
inline fun <KEY, T> SavedStateHandle.requireValueClass(key: String, mapToValueClass: (KEY) -> T?): T = requireNotNull(getValueClass(key, mapToValueClass)) { createSaveStateErrorMessage(key) }

fun SavedStateHandle.getDecodedString(key: String): String? = get<String>(key)?.let { URLDecoder.decode(it, "utf-8") }
fun SavedStateHandle.requireDecodedString(key: String): String = URLDecoder.decode(requireNotNull(get<String>(key)) { createSaveStateErrorMessage(key) }, "utf-8")

// custom extension for this app
fun SavedStateHandle.getIndividualId(key: String): IndividualId? = getValueClass<String, IndividualId>(key) { IndividualId(it) }
fun SavedStateHandle.requireIndividualId(key: String): IndividualId = requireValueClass<String, IndividualId>(key) { IndividualId(it) }
