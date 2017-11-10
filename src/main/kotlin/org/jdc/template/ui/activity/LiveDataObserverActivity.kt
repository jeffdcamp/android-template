package org.jdc.template.ui.activity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity

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