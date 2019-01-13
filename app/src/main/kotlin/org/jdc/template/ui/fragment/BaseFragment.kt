package org.jdc.template.ui.fragment

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseFragment : LiveDataObserverFragment(), CoroutineScope by MainScope() {
    fun enableActionBarBackArrow(enable: Boolean) {
        activity?.actionBar?.apply {
            setHomeButtonEnabled(enable)
            setDisplayHomeAsUpEnabled(enable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel() // CoroutineScope.cancel
    }
}