package org.jdc.template.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

inline fun <E> LifecycleOwner.receive(channel: ReceiveChannel<E>, crossinline block: suspend (E) -> Unit) {
    lifecycleScope.launch {
        for (e in channel) {
            block(e)
        }
    }
}

inline fun <E> LifecycleOwner.receiveWhenCreated(channel: ReceiveChannel<E>, crossinline block: suspend (E) -> Unit) {
    lifecycleScope.launchWhenCreated {
        for (e in channel) {
            block(e)
        }
    }
}

/**
 * Recommended for the majority of use cases
 */
inline fun <E> LifecycleOwner.receiveWhenStarted(channel: ReceiveChannel<E>, crossinline block: suspend (E) -> Unit) {
    lifecycleScope.launchWhenStarted {
        for (e in channel) {
            block(e)
        }
    }
}

inline fun <E> LifecycleOwner.receiveWhenResumed(channel: ReceiveChannel<E>, crossinline block: suspend (E) -> Unit) {
    lifecycleScope.launchWhenResumed {
        for (e in channel) {
            block(e)
        }
    }
}