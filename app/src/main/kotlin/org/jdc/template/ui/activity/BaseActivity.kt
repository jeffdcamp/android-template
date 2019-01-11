package org.jdc.template.ui.activity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : LiveDataObserverActivity(), CoroutineScope by MainScope() {

    fun enableActionBarBackArrow(enable: Boolean) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(enable)
            actionBar.setDisplayHomeAsUpEnabled(enable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel() // CoroutineScope.cancel
    }
}
