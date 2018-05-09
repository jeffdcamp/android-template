package org.jdc.template.ui.fragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment

abstract class LiveDataObserverFragment : Fragment() {
    protected inline fun <T> LiveData<T>.observe(crossinline block: (T?) -> Unit) {
        observe(this@LiveDataObserverFragment, Observer { block(it) })
    }

    protected inline fun <T> LiveData<T>.observeNotNull(crossinline block: (T) -> Unit) {
        observe(this@LiveDataObserverFragment, Observer {
            it ?: return@Observer
            block(it)
        })
    }
}