package org.jdc.template.util.ext

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import java.net.URLDecoder

@VisibleForTesting
fun createSaveStateErrorMessage(key: String) = "Missing SavedState value for Key: $key"

fun <T> SavedStateHandle.require(key: String): T = requireNotNull(get<T>(key)) { createSaveStateErrorMessage(key) }

inline fun <KEY, T> SavedStateHandle.getValueClass(key: String, mapToValueClass: (KEY) -> T?): T? = get<KEY>(key)?.let { mapToValueClass(it) }
inline fun <KEY, T> SavedStateHandle.requireValueClass(key: String, mapToValueClass: (KEY) -> T?): T = requireNotNull(getValueClass(key, mapToValueClass)) { createSaveStateErrorMessage(key) }

fun SavedStateHandle.getDecodedString(key: String): String? = get<String>(key)?.let { URLDecoder.decode(it, "utf-8") }
fun SavedStateHandle.requireDecodedString(key: String): String = URLDecoder.decode(requireNotNull(get<String>(key)) { createSaveStateErrorMessage(key) }, "utf-8")
