package org.jdc.template.ui.fragment

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

abstract class BaseFragment : LiveDataObserverFragment(), CoroutineScope {
    private val baseFragmentJob = Job() // create a job as a parent for coroutines

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + baseFragmentJob

    fun enableActionBarBackArrow(enable: Boolean) {
        activity?.actionBar?.apply {
            setHomeButtonEnabled(enable)
            setDisplayHomeAsUpEnabled(enable)
        }
    }

    override fun onDestroy() {
        baseFragmentJob.cancel()
        super.onDestroy()
    }
}