package org.jdc.template.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

inline fun <T> LifecycleOwner.collect(flow: Flow<T>, crossinline block: suspend (T) -> Unit) {
    lifecycleScope.launch {
        flow.collect {
            block(it)
        }
    }
}

inline fun <T> LifecycleOwner.collectWhenCreated(flow: Flow<T>, crossinline block: suspend (T) -> Unit) {
    lifecycleScope.launchWhenCreated {
        flow.collect {
            block(it)
        }
    }
}

/**
 * Recommended for the majority of use cases
 */
inline fun <T> LifecycleOwner.collectWhenStarted(flow: Flow<T>, crossinline block: suspend (T) -> Unit) {
    lifecycleScope.launchWhenStarted {
        flow.collect {
            block(it)
        }
    }
}

inline fun <T> LifecycleOwner.collectWhenResumed(flow: Flow<T>, crossinline block: suspend (T) -> Unit) {
    lifecycleScope.launchWhenResumed {
        flow.collect {
            block(it)
        }
    }
}
