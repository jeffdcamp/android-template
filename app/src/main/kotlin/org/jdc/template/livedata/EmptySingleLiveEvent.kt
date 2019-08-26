@file:Suppress("unused")

package org.jdc.template.livedata

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 *
 *
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 *
 *
 * Note that only one observer is going to be notified of changes.
 */
class EmptySingleLiveEvent : SingleLiveEvent<Unit>() {

    @MainThread
    fun call() {
        super.setValue(Unit)
    }

    @WorkerThread
    fun postCall() {
        super.postValue(Unit)
    }
}
