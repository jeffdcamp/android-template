package org.jdc.template.util.data

import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutineDispatchers(
    val default: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher,
)
