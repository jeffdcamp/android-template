package org.jdc.template.ui.fragment

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

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