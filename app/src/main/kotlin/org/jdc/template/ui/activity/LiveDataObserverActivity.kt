package org.jdc.template.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity

abstract class LiveDataObserverActivity : AppCompatActivity() {
    protected inline fun <T> LiveData<T>.observe(crossinline block: (T?) -> Unit) {
        observe(this@LiveDataObserverActivity, Observer { block(it) })
    }

    protected inline fun <T> LiveData<T>.observeNotNull(crossinline block: (T) -> Unit) {
        observe(this@LiveDataObserverActivity, Observer {
            it ?: return@Observer
            block(it)
        })
    }
}