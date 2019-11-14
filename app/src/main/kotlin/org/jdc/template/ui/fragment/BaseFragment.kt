package org.jdc.template.ui.fragment

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import timber.log.Timber

abstract class BaseFragment : Fragment() {
    @MainThread
    protected inline fun <T> LiveData<T>.observe(crossinline onChanged: (T?) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value ->
            onChanged.invoke(value)
        }
        observe(viewLifecycleOwner, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T : Any> LiveData<T>.observeKt(crossinline onChanged: (T) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value ->
            if (value != null) {
                onChanged.invoke(value)
            } else {
                Timber.w("Cannot post null value to a non-null LiveData")
            }
        }
        observe(this@BaseFragment, wrappedObserver)
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
        observe(this@BaseFragment, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T : Any> LiveData<T>.observeByFragmentKt(crossinline onChanged: (T) -> Unit): Observer<T> {
        val wrappedObserver = Observer<T> { value ->
            if (value != null) {
                onChanged.invoke(value)
            } else {
                Timber.i("Cannot post null value to a non-null LiveData")
            }
        }
        observe(this@BaseFragment, wrappedObserver)
        return wrappedObserver
    }

    @MainThread
    protected inline fun <T> LiveData<T?>.observeNotNullByFragment(crossinline onChanged: (T) -> Unit): Observer<T?> {
        val wrappedObserver = Observer<T?> { value ->
            if (value != null) {
                onChanged.invoke(value)
            }
        }
        observe(this@BaseFragment, wrappedObserver)
        return wrappedObserver
    }
}