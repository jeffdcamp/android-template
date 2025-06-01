package org.jdc.template.shared.util.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlin.experimental.ExperimentalTypeInference

/**
 * Invokes [stateIn] with the recommended settings
 */
fun <T> Flow<T>.stateInDefault(coroutineScope: CoroutineScope, initialValue: T): StateFlow<T> = this.stateIn(
    scope = coroutineScope,
    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
    initialValue = initialValue,
)

/**
 * Invokes [shareIn] with the recommended settings
 */
fun <T> Flow<T>.shareInDefaults(coroutineScope: CoroutineScope): SharedFlow<T> = this.shareIn(
    coroutineScope,
    SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
    replay = 1
)

/**
 * Applies a [map] transform to a flow, only emitting new values when the value is distinct.
 */
@OptIn(ExperimentalTypeInference::class)
inline fun <T, R> Flow<T>.mapDistinct(@BuilderInference crossinline transform: suspend (value: T) -> R) = this.map(transform).distinctUntilChanged()