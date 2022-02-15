@file:Suppress("unused")

package org.jdc.template.util.delegates

import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private class MutableNullableSavedStateHandleDelegate<T : Any?>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String
) : ReadWriteProperty<Any, T> {
    private val defaultValue: T? = null

    @Suppress("UNCHECKED_CAST") // This is an unnecessary cast to handle kotlin's null safety
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return savedStateHandle.get(key) ?: defaultValue as T
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        return savedStateHandle.set(key, value)
    }
}

private class MutableNonNullSavedStateHandleDelegate<T>(
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

private class RequireNonNullSavedStateHandleDelegate<T>(
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

class SavedStateLoader<T>(
    private val savedStateHandle: SavedStateHandle,
    private val required: Boolean,
    private val key: String?,
    private val default: T? = null,
) {
    operator fun provideDelegate(
        thisRef: Any,
        property: KProperty<*>
    ): ReadWriteProperty<Any, T> {
        val keyActual = key ?: property.name
        // NOTE: This can be done through reflection: property.returnType.isMarkedNullable
        // This is cooler and way more awesome To be added if we can find more good use cases of reflection.
        // To be approved by Team Leads
        return when {
            required -> RequireNonNullSavedStateHandleDelegate(savedStateHandle, keyActual)
            default != null -> MutableNonNullSavedStateHandleDelegate(savedStateHandle, keyActual, default)
            else -> MutableNullableSavedStateHandleDelegate(savedStateHandle, keyActual)
        }
    }
}

/**
 * Provides a non-null read/write accessor for the Saved State value with the given key.
 *
 * Usage: (var|val) savedValue: T by savedState(savedStateHandle, "KEY", DEFAULT_T)
 *
 * @param savedStateHandle The [SavedStateHandle]
 * @param defaultValue The fallback value if the value doesn't exist (is null) in the [SavedStateHandle]
 * @param key The key of the value to be accessed or null to pull the value from the property
 *
 * @return A property delegate that reads from and writes to the SavedStateHandle
 */
fun <T : Any> savedState(savedStateHandle: SavedStateHandle, defaultValue: T, key: String? = null): SavedStateLoader<T> = SavedStateLoader(savedStateHandle, required = false, key, defaultValue)

/**
 * Provides a nullable read/write accessor for the Saved State value with the given key. The default value
 * of the accessor is null.
 *
 * Usage: (var|val) savedValue: T? by savedState(savedStateHandle, "KEY")
 *
 * @param savedStateHandle The [SavedStateHandle]
 * @param key The key of the value to be accessed or null to pull the value from the property
 *
 * @return A nullable property delegate that reads from and writes to the SavedStateHandle
 */
fun <T : Any?> savedState(savedStateHandle: SavedStateHandle, key: String? = null): SavedStateLoader<T> = SavedStateLoader(savedStateHandle, required = false, key)

/**
 * Provides a non-null read/write accessor for the [SavedStateHandle] that throws an [IllegalArgumentException] if SavedStateHandle.get(key) = null
 *
 * Usage: (var|val) savedValue: T by requireSavedState(savedStateHandle, "KEY")
 *
 * @param savedStateHandle The [SavedStateHandle]
 * @param key The key of the value to be accessed or null to pull the value from the property
 *
 * @return A property delegate that reads from and writes to the SavedStateHandle
 */
fun <T : Any> requireSavedState(savedStateHandle: SavedStateHandle, key: String? = null): SavedStateLoader<T> = SavedStateLoader(savedStateHandle, required = true, key)