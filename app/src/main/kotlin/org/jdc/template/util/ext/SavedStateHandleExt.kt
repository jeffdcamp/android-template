package org.jdc.template.util.ext

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import java.net.URLDecoder

@VisibleForTesting
fun createSaveStateErrorMessage(key: String) = "Missing SavedState value for Key: $key"

fun SavedStateHandle.requireBoolean(key: String): Boolean = requireNotNull(get<Boolean>(key)) { createSaveStateErrorMessage(key) }
fun SavedStateHandle.requireInt(key: String): Int = requireNotNull(get<Int>(key)) { createSaveStateErrorMessage(key) }
fun SavedStateHandle.requireLong(key: String): Long = requireNotNull(get<Long>(key)) { createSaveStateErrorMessage(key) }
fun SavedStateHandle.requireString(key: String): String = requireNotNull(get<String>(key)) { createSaveStateErrorMessage(key) }
fun <T> SavedStateHandle.require(key: String): T = requireNotNull(get<T>(key)) { createSaveStateErrorMessage(key) }

inline fun <T> SavedStateHandle.getValueClassBoolean(key: String, mapToValueClass: (Int) -> T?): T? = getValueClass<Int, T>(key, mapToValueClass)
inline fun <T> SavedStateHandle.requireValueClassBoolean(key: String, mapToValueClass: (Int) -> T?): T = requireValueClass<Int, T>(key, mapToValueClass)
inline fun <T> SavedStateHandle.getValueClassInt(key: String, mapToValueClass: (Int) -> T?): T? = getValueClass<Int, T>(key, mapToValueClass)
inline fun <T> SavedStateHandle.requireValueClassInt(key: String, mapToValueClass: (Int) -> T?): T = requireValueClass<Int, T>(key, mapToValueClass)
inline fun <T> SavedStateHandle.getValueClassLong(key: String, mapToValueClass: (Int) -> T?): T? = getValueClass<Int, T>(key, mapToValueClass)
inline fun <T> SavedStateHandle.requireValueClassLong(key: String, mapToValueClass: (Int) -> T?): T = requireValueClass<Int, T>(key, mapToValueClass)
inline fun <T> SavedStateHandle.getValueClassString(key: String, mapToValueClass: (String) -> T?): T? = getValueClass<String, T>(key, mapToValueClass)
inline fun <T> SavedStateHandle.requireValueClassString(key: String, mapToValueClass: (String) -> T?): T = requireValueClass<String, T>(key, mapToValueClass)

inline fun <S, T> SavedStateHandle.getValueClass(key: String, mapToValueClass: (S) -> T?): T? = get<S>(key)?.let { mapToValueClass(it) }
inline fun <S, T> SavedStateHandle.requireValueClass(key: String, mapToValueClass: (S) -> T?): T = requireNotNull(getValueClass(key, mapToValueClass)) { createSaveStateErrorMessage(key) }

fun SavedStateHandle.getDecodedString(key: String): String? = get<String>(key)?.let { URLDecoder.decode(it, "utf-8") }
fun SavedStateHandle.requireDecodedString(key: String): String = URLDecoder.decode(requireNotNull(get<String>(key)) { createSaveStateErrorMessage(key) }, "utf-8")
