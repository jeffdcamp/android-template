@file:Suppress("LeakingThis", "MemberVisibilityCanPrivate", "Unused")

package org.jdc.template.model.prefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

sealed class PrefsManager {
    protected fun addManagedContainer(container: PrefsContainer): PrefsContainer {
        if (!prefsMap.containsKey(container.namespace)) {
            prefsMap[container.namespace] = container
        } else {
            if (!(prefsMap[container.namespace] === container))
                throw RuntimeException("A namespace with the key ${container.namespace} already exists.")
        }

        return prefsMap[container.namespace] ?: error("Container was null when it should not have been")
    }

    protected fun getManagedContainer(namespace: String): SharedPreferences {
        prefsMap[namespace]?.let {
            return if (namespace == COMMON_NAMESPACE && !isTest) {
                PreferenceManager.getDefaultSharedPreferences(context)
            } else {
                context.getSharedPreferences("${context.packageName}.${it.namespace}", it.privacy)
            }
        } ?: error("Container was null when it should not have been")
    }

    companion object {
        const val COMMON_NAMESPACE = "COMMON_NAMESPACE"
        const val PROTECTED_NAMESPACE = "PROTECTED_NAMESPACE"

        private val protectedNamespace = hashSetOf(PROTECTED_NAMESPACE)

        private val prefsMap = mutableMapOf<String, PrefsContainer>()

        private var context: Context by Delegates.notNull()
        private var isTest: Boolean = false

        fun init(context: Context, isTest: Boolean = false) {
            this.context = context
            this.isTest = isTest
        }

        fun protectedNamespace(namespace: String) {
            protectedNamespace.add(namespace)
        }

        fun unprotectNamespace(namespace: String) {
            if (namespace != PROTECTED_NAMESPACE) protectedNamespace.remove(namespace)
        }

        fun clear(namespace: String? = null) {
            if (!namespace.isNullOrBlank()) {
                prefsMap[namespace]?.clear()
            } else {
                prefsMap.filterKeys { it !in protectedNamespace }
                    .forEach { it.value.clear() }
            }
        }

        fun reset() {
            prefsMap.clear()
        }
    }
}

abstract class PrefsContainer(val namespace: String, val privacy: Int = Context.MODE_PRIVATE) : PrefsManager() {
    init {
        addManagedContainer(this)
    }

    val preferenceManager: SharedPreferences by lazy {
        getManagedContainer(namespace)
    }

    @SuppressLint("ApplySharedPref")
    fun clear() {
        preferenceManager.edit(commit = true) {
            clear()
        }
        onCleared()
    }

    protected open fun onCleared() {

    }

    protected inner class NullableStringPref(
        private val key: String? = null,
        private val transformer: PreferenceTransformer<String>? = null,
        private val onChange: ((String?) -> Unit)? = null
    ) : ReadWriteProperty<Any, String?> {

        constructor(
            key: String? = null,
            transformer: PreferenceTransformer<String>? = null,
            stateFlow: MutableStateFlow<String?>? = null,
            onChange: ((String?) -> Unit)? = null
        ) : this(key, transformer, {
            stateFlow?.value = it
            onChange?.invoke(it)
        })

        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: Any, property: KProperty<*>): String? {
            val name = key ?: property.name
            return if (transformer != null) {
                transformer.decode(preferenceManager.getString(name, null))
            } else {
                preferenceManager.getString(name, null)
            }
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
            val name = key ?: property.name
            preferenceManager.edit {
                if (transformer != null) {
                    putString(name, transformer.encode(value))
                } else {
                    putString(name, value)
                }
            }
            onChange?.invoke(value)
        }
    }

    @Suppress("FunctionName")
    protected inline fun <reified T : Enum<T>> PrefsContainer.EnumPref(
        defaultValue: T,
        key: String? = null,
        transformer: PreferenceTransformer<String>? = null,
        stateFlow: MutableStateFlow<T>?,
        noinline onChange: ((T) -> Unit)? = null
    ) = EnumPref(defaultValue, key, transformer, {
        stateFlow?.value = it
        onChange?.invoke(it)
    })

    @Suppress("FunctionName")
    protected inline fun <reified T : Enum<T>> PrefsContainer.EnumPref(
        defaultValue: T,
        key: String? = null,
        transformer: PreferenceTransformer<String>? = null,
        noinline onChange: ((T) -> Unit)? = null
    ): ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            val prefName = key ?: property.name

            val name: String? = if (transformer != null) {
                transformer.decode(preferenceManager.getString(prefName, defaultValue.name))
            } else {
                preferenceManager.getString(prefName, defaultValue.name)
            }

            return try {
                name?.let { enumValueOf<T>(name) } ?: defaultValue
            } catch (_: Exception) {
                defaultValue
            }
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            val name = key ?: property.name

            preferenceManager.edit {
                if (transformer != null) {
                    putString(name, transformer.encode(value.name))
                } else {
                    putString(name, value.name)
                }
            }

            onChange?.invoke(value)
        }
    }

    protected open inner class SharedPref<T>(
        private val defaultValue: T,
        private val key: String? = null,
        private val transformer: PreferenceTransformer<T>? = null,
        private val onChange: ((T) -> Unit)? = null
    ) : ReadWriteProperty<Any, T> {

        constructor(
            defaultValue: T,
            key: String? = null,
            transformer: PreferenceTransformer<T>? = null,
            stateFlow: MutableStateFlow<T>? = null,
            onChange: ((T) -> Unit)? = null
        ) : this(defaultValue, key, transformer, {
            stateFlow?.value = it
            onChange?.invoke(it)
        })

        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            val name = key ?: property.name

            return if (transformer != null) {
                transformer.decode(preferenceManager.getString(name, null)) ?: defaultValue
            } else {
                when (defaultValue) {
                    is Boolean -> preferenceManager.getBoolean(name, defaultValue) as T
                    is Float -> preferenceManager.getFloat(name, defaultValue) as T
                    is Int -> preferenceManager.getInt(name, defaultValue) as T
                    is Long -> preferenceManager.getLong(name, defaultValue) as T
                    is String -> preferenceManager.getString(name, defaultValue) as T
                    else -> throw UnsupportedOperationException("Unsupported preference type ${property.javaClass} on property $name")
                }
            }
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            val name = key ?: property.name

            preferenceManager.edit {
                if (transformer != null) {
                    putString(name, transformer.encode(value))
                } else {
                    when (defaultValue) {
                        is Boolean -> putBoolean(name, value as Boolean)
                        is Float -> putFloat(name, value as Float)
                        is Int -> putInt(name, value as Int)
                        is Long -> putLong(name, value as Long)
                        is String -> putString(name, value as String)
                        else -> throw UnsupportedOperationException("Unsupported preference type ${property.javaClass} on property $name")
                    }
                }
            }

            onChange?.invoke(value)
        }
    }
}

abstract class PreferenceTransformer<T> {
    abstract fun encode(value: T?): String?
    abstract fun decode(value: String?): T?
}