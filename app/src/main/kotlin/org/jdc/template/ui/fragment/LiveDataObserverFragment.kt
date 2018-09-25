package org.jdc.template.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment

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