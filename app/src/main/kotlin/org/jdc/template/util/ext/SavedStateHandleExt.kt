package org.jdc.template.util.ext

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import java.net.URLDecoder

@VisibleForTesting
fun createSaveStateErrorMessage(key: String) = "Missing SavedState value for Key: $key"

fun SavedStateHandle.requireInt(key: String): Int = requireNotNull(get<Int>(key)) { createSaveStateErrorMessage(key) }
fun SavedStateHandle.requireString(key: String): String = requireNotNull(get<String>(key)) { createSaveStateErrorMessage(key) }

fun SavedStateHandle.decodedString(key: String): String? = get<String>(key)?.let { URLDecoder.decode(it, "utf-8") }
fun SavedStateHandle.requireDecodedString(key: String): String = URLDecoder.decode(requireNotNull(get<String>(key)) { createSaveStateErrorMessage(key) }, "utf-8")

inline fun <T> SavedStateHandle.getValueClassString(key: String, mapToValueClass: (String) -> T?): T? = getValueClass<T, String>(key, mapToValueClass)
inline fun <T> SavedStateHandle.requireValueClassString(key: String, mapToValueClass: (String) -> T?): T = requireValueClass<T, String>(key, mapToValueClass)
inline fun <T> SavedStateHandle.getValueClassInt(key: String, mapToValueClass: (Int) -> T?): T? = getValueClass<T, Int>(key, mapToValueClass)
inline fun <T> SavedStateHandle.requireValueClassInt(key: String, mapToValueClass: (Int) -> T?): T = requireValueClass<T, Int>(key, mapToValueClass)

inline fun <T, S> SavedStateHandle.getValueClass(key: String, mapToValueClass: (S) -> T?): T? = get<S>(key)?.let { mapToValueClass(it) }
inline fun <T, S> SavedStateHandle.requireValueClass(key: String, mapToValueClass: (S) -> T?): T = requireNotNull(getValueClass(key, mapToValueClass)) { createSaveStateErrorMessage(key) }