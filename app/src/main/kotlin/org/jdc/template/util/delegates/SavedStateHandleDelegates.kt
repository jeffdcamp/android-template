@file:Suppress("unused")

package org.jdc.template.util.delegates

import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Provides a non-null read/write accessor for the Saved State value with the given key.
 *
 * Usage: (var|val) savedValue: T by savedState(savedStateHandle, "KEY", DEFAULT_T)
 *
 * @param savedStateHandle The [SavedStateHandle]
 * @param key The key of the value to be accessed
 * @param defaultValue The fallback value if the value doesn't exist (is null) in the [SavedStateHandle]
 *
 * @return A property delegate that reads from and writes to the SavedStateHandle
 */
fun <T : Any> savedState(savedStateHandle: SavedStateHandle, key: String, defaultValue: T): ReadWriteProperty<Any, T> = MutableNonNullSavedStateHandleDelegate(savedStateHandle, key, defaultValue)

/**
 * Provides a nullable read/write accessor for the Saved State value with the given key. The default value
 * of the accessor is null.
 *
 * Usage: (var|val) savedValue: T? by savedState(savedStateHandle, "KEY")
 *
 * @param savedStateHandle The [SavedStateHandle]
 * @param key The key of the value to be accessed
 *
 * @return A nullable property delegate that reads from and writes to the SavedStateHandle
 */
fun <T : Any?> savedState(savedStateHandle: SavedStateHandle, key: String): ReadWriteProperty<Any, T?> = MutableNonNullSavedStateHandleDelegate(savedStateHandle, key, null)

/**
 * Provides a non-null read/write accessor for the [SavedStateHandle] that throws an [IllegalArgumentException] if SavedStateHandle.get(key) = null
 *
 * Usage: (var|val) savedValue: T by requireSavedState(savedStateHandle, "KEY")
 *
 * @param savedStateHandle The [SavedStateHandle]
 * @param key The key of the value to be accessed
 *
 * @return A property delegate that reads from and writes to the SavedStateHandle
 */
fun <T : Any> requireSavedState(savedStateHandle: SavedStateHandle, key: String): ReadWriteProperty<Any, T> = RequireNonNullSavedStateHandleDelegate(savedStateHandle, key)

private class MutableNonNullSavedStateHandleDelegate<T>
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    private val defaultValue: T
) : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return savedStateHandle.get(key) ?: defaultValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        return savedStateHandle.set(key, value)
    }
}

private class RequireNonNullSavedStateHandleDelegate<T : Any>
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val key: String
) : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return requireNotNull(savedStateHandle.get(key)) { "savedStateHandle[$key] cannot be null in $thisRef" }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        return savedStateHandle.set(key, value)
    }
}

