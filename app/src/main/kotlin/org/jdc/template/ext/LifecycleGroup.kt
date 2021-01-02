package org.jdc.template.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

class LifecycleGroup(val lifecycleOwner: LifecycleOwner) {
    /**
     * Recommended for the majority of use cases
     * The crucial difference from [collect] is that when the original flow emits a new value, [block] for previous
     * value is cancelled.
     **/
    inline fun <T> Flow<T>.collectLatestWhenStarted(crossinline block: suspend (T) -> Unit): Job {
        return lifecycleOwner.lifecycleScope.launchWhenStarted {
            this@collectLatestWhenStarted.collectLatest {
                block(it)
            }
        }
    }
    /**
     * Recommended for the majority of use cases
     */
    inline fun <T> ReceiveChannel<T>.receiveWhenStarted(crossinline block: suspend (T) -> Unit): Job {
        return lifecycleOwner.lifecycleScope.launchWhenStarted {
            for (event in this@receiveWhenStarted) {
                block(event)
            }
        }
    }
    /**
     * Recommended for when every emit needs to be processed to completion.
     */
    inline fun <T> Flow<T>.collectWhenStarted(crossinline block: suspend (T) -> Unit): Job {
        return lifecycleOwner.lifecycleScope.launchWhenStarted {
            this@collectWhenStarted.collect {
                block(it)
            }
        }
    }
    /**
     * Recommended for collecting inside a fragment in a viewpager when only the active fragment should receive events.
     * If fragments need to be updated in the background use [collectLatestWhenStarted]
     */
    inline fun <T> Flow<T>.collectLatestWhenResumed(crossinline block: suspend (T) -> Unit): Job {
        return lifecycleOwner.lifecycleScope.launchWhenResumed {
            this@collectLatestWhenResumed.collectLatest {
                block(it)
            }
        }
    }
    /**
     * Recommended for collecting inside a fragment in a viewpager and you need to process every emit to completion
     */
    inline fun <T> Flow<T>.collectWhenResumed(crossinline block: suspend (T) -> Unit): Job {
        return lifecycleOwner.lifecycleScope.launchWhenResumed {
            this@collectWhenResumed.collect {
                block(it)
            }
        }
    }
    /**
     * Recommended for receiving inside a fragment in a viewpager as only the active Fragment will be affected.
     */
    inline fun <T> ReceiveChannel<T>.receiveWhenResumed(crossinline block: suspend (T) -> Unit): Job {
        return lifecycleOwner.lifecycleScope.launchWhenResumed {
            for (e in this@receiveWhenResumed) {
                block(e)
            }
        }
    }
}

inline fun withLifecycleOwner(lifecycleOwner: LifecycleOwner, block: LifecycleGroup.() -> Unit) {
    LifecycleGroup(lifecycleOwner).run(block)
}