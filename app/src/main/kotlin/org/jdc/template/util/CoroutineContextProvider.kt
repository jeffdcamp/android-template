package org.jdc.template.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val main: CoroutineContext // Android Main/UI thread
    val default: CoroutineContext // Normal non-blocking background work
    val io: CoroutineContext // Blocking background work (network calls, heavy blocking database operations, etc)

    object MainCoroutineContextProvider : CoroutineContextProvider {
        override val main: CoroutineContext
            get() = Dispatchers.Main
        override val default: CoroutineContext
            get() = Dispatchers.Default
        override val io: CoroutineContext
            get() = Dispatchers.IO
    }

    object TestCoroutineContextProvider : CoroutineContextProvider {
        override val main: CoroutineContext
            get() = Dispatchers.Default
        override val default: CoroutineContext
            get() = Dispatchers.Default
        override val io: CoroutineContext
            get() = Dispatchers.Default
    }

    object TestJDBCCoroutineContextProvider : CoroutineContextProvider {
        private val singleThreadCoroutineContext = newSingleThreadContext("Test Context")

        override val main: CoroutineContext
            get() = singleThreadCoroutineContext
        override val default: CoroutineContext
            get() = singleThreadCoroutineContext
        override val io: CoroutineContext
            get() = singleThreadCoroutineContext
    }
}

