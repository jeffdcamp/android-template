package org.jdc.template.ui.activity

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity

abstract class LiveDataObserverActivity : AppCompatActivity() {
    @MainThread
    protected inline fun <T> LiveData<T>.observe(crossinline onChanged: (T?) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value -> onChanged.invoke(value) }
        observe(this@LiveDataObserverActivity, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T> LiveData<T?>.observeNotNull(crossinline onChanged: (T) -> Unit): Observer<T?> {
        val wrappedObserver = Observer<T?> { value ->
            if (value != null) {
                onChanged.invoke(value)
            }
        }
        observe(this@LiveDataObserverActivity, wrappedObserver)
        return wrappedObserver
    }
}