package org.jdc.template.ui.mvp

import kotlinx.coroutines.experimental.Job
import org.jdc.template.util.CompositeJob

abstract class BasePresenter {
    val compositeJob = CompositeJob()

    // use init() function to pass the VIEW and all other variables to initialize the presenter
    // fun init(view, id) { }

    /**
     * Usually called at the end of onCreate() to have the presenter start loading data into the view
     *
     * @return Job a coroutine job is returned so that Unit tests can job.join() to allow suspend.  Return null if there is no job
     */
    open fun load(): Job? {
        return null
    }

    /**
     * Usually called in onResume() to have the presenter reload data, if needed, into the view
     *
     * @return Job a coroutine job is returned so that Unit tests can job.join() to allow suspend. Return null if there is no job
     */
    open fun reload(forceRefresh: Boolean = false): Job? {
        return null
    }

    /**
     * Usually called in onStart() to have the presenter register event bus, listeners, observables, etc
     */
    open fun register() {
    }

    /**
     * Usually called in onStop() to have the presenter unregister event bus, listeners, observables, etc
     */
    open fun unregister() {
        compositeJob.cancelAndClearAll()
    }
}