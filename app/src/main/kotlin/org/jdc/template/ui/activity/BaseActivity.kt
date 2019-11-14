package org.jdc.template.ui.activity

import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    @MainThread
    protected inline fun <T> LiveData<T>.observe(crossinline onChanged: (T?) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value -> onChanged.invoke(value) }
        observe(this@BaseActivity, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T : Any> LiveData<T>.observeKt(crossinline onChanged: (T) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value ->
            if (value != null) {
                onChanged.invoke(value)
            } else {
                Timber.i("Cannot post null value to a non-null LiveData")
            }
        }
        observe(this@BaseActivity, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T> LiveData<T?>.observeNotNull(crossinline onChanged: (T) -> Unit): Observer<T?> {
        val wrappedObserver = Observer<T?> { value ->
            if (value != null) {
                onChanged.invoke(value)
            }
        }
        observe(this@BaseActivity, wrappedObserver)
        return wrappedObserver
    }
}
