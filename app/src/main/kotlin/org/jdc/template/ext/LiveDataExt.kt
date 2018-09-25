package org.jdc.template.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.annotation.AnyThread
import androidx.annotation.MainThread

/**
 * Observe for Activity or Fragment
 */
inline fun <T> LiveData<T>.observe(owner: LifecycleOwner, crossinline block: (T?) -> Unit) {
    observe(owner, Observer {
        block(it)
    })
}

/**
 * Observe for Activity or Fragment only when value is not null
 */
inline fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, crossinline block: (T) -> Unit) {
    observe(owner, Observer {
        it ?: return@Observer
        block(it)
    })
}

/**
 * Notify observers that, even though value did not change, its mutable contents may have changed.
 * Can be called only on the main thread.
 */
@MainThread
fun <T> MutableLiveData<T>.touch() {
    value = value
}

/**
 * Notify observers that, even though value did not change, its mutable contents may have changed.
 * Can be called an any thread.
 */
@AnyThread
fun <T> MutableLiveData<T>.postTouch() {
    postValue(value)
}

/**
 * Create MutableLiveData and set the value right away. Can be called on any thread.
 */
fun <T> mutableLiveData(value: T?): MutableLiveData<T> = MutableLiveData<T>().apply {
    postValue(value)
}
