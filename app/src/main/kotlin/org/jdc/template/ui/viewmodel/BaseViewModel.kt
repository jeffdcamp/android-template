package org.jdc.template.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.jdc.template.util.CoroutineContextProvider
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(
    protected val cc: CoroutineContextProvider,
    private val defaultContext: CoroutineContext = cc.default
) : ViewModel(), CoroutineScope {
    protected val baseViewModelJob = Job() // create a job as a parent for coroutines

    override val coroutineContext: CoroutineContext
        get() = defaultContext + baseViewModelJob

    override fun onCleared() {
        baseViewModelJob.cancel()
        super.onCleared()
    }
}