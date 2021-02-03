/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdc.template.util.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A lazy property that gets cleaned up when the fragment's view is destroyed.
 *
 * Accessing this variable while the fragment's view is destroyed will throw NPE.
 */
private class AutoClearedValue<T>(val fragment: Fragment, val onClear: (T) -> Unit) : ReadWriteProperty<Fragment, T> {
    private var _value: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            _value?.let { onClear(it) }
                            _value = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return checkNotNull(_value) { "Should never call auto-cleared-value get when it might not be available" }
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

/**
 * A lazy property that gets cleaned up when the fragment's view is destroyed.
 *
 * Accessing this variable while the fragment's view is destroyed will NOT throw an exception but return null.
 */
private class AutoClearedNullableValue<T: Any?>(val fragment: Fragment, val onClear: (T) -> Unit) : ReadWriteProperty<Fragment, T> {
    private var _value: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            _value?.let { onClear(it) }
                            _value = null
                        }
                    })
                }
            }
        })
    }

    @Suppress("UNCHECKED_CAST") // This is an unnecessary cast to handle kotlin's null safety
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value as T
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

class AutoClearedLoader<T>(private val nullable: Boolean, private val onClear: (T) -> Unit) {
    operator fun provideDelegate(
        thisRef: Fragment,
        property: KProperty<*>
    ): ReadWriteProperty<Fragment, T> {
        // NOTE: This can be done through reflection: property.returnType.isMarkedNullable
        // This is cooler and way more awesome To be added if we can find more good use cases of reflection.
        // To be approved by Team Leads
        return if (nullable) {
            AutoClearedNullableValue(thisRef, onClear)
        } else {
            AutoClearedValue(thisRef, onClear)
        }
    }
}

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 * This should be used for Bindings, ViewPager Adapters, and RecyclerViewAdapters
 * (Any View Adapter/Binding in a fragment)
 *
 * @param onClear called before the value is null'd out if not null
 */
@Suppress("unused") // Used to scope to Fragments
fun <T: Any> Fragment.autoCleared(onClear: (T) -> Unit = {}): AutoClearedLoader<T> = AutoClearedLoader(nullable = false, onClear)
/**
 * Creates an [AutoClearedValue] associated with this fragment.
 * This should be used for Bindings, ViewPager Adapters, and RecyclerViewAdapters, MenuItems
 * (Any View Adapter/Binding in a fragment)
 *
 * @param onClear called before the value is null'd out if not null
 */
@Suppress("unused") // Used to scope to Fragments
fun <T: Any?> Fragment.autoClearedNullable(onClear: (T?) -> Unit = {}): AutoClearedLoader<T?> = AutoClearedLoader(nullable = true, onClear)