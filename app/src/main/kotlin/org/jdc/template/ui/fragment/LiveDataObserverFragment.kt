package org.jdc.template.ui.fragment

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment

abstract class LiveDataObserverFragment : Fragment() {
    @MainThread
    protected inline fun <T> LiveData<T>.observe(crossinline onChanged: (T?) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value ->
            onChanged.invoke(value)
        }
        observe(viewLifecycleOwner, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T> LiveData<T?>.observeNotNull(crossinline onChanged: (T) -> Unit): Observer<T?> {
        val wrappedObserver = Observer<T?> { value ->
            if (value != null) {
                onChanged.invoke(value)
            }
        }
        observe(viewLifecycleOwner, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T> LiveData<T>.observeByFragment(crossinline onChanged: (T?) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value -> onChanged.invoke(value) }
        observe(this@LiveDataObserverFragment, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T> LiveData<T?>.observeNotNullByFragment(crossinline onChanged: (T) -> Unit): Observer<T?> {
        val wrappedObserver = Observer<T?> { value ->
            if (value != null) {
                onChanged.invoke(value)
            }
        }
        observe(this@LiveDataObserverFragment, wrappedObserver)
        return wrappedObserver
    }
}