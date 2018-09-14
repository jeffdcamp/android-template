package org.jdc.template.ui.activity

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

abstract class BaseActivity : LiveDataObserverActivity(), CoroutineScope {
    private val baseActivityJob = Job() // create a job as a parent for coroutines

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + baseActivityJob

    fun enableActionBarBackArrow(enable: Boolean) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(enable)
            actionBar.setDisplayHomeAsUpEnabled(enable)
        }
    }

    override fun onDestroy() {
        baseActivityJob.cancel()
        super.onDestroy()
    }
}
