package org.jdc.template.util

import kotlinx.coroutines.experimental.CancellationException
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.newSingleThreadContext
import kotlin.coroutines.experimental.CoroutineContext

interface CoroutineContextProvider {
    val ui: CoroutineContext
    val network: CoroutineContext
    val commonPool: CoroutineContext

    object MainCoroutineContextProvider : CoroutineContextProvider {
        override val ui: CoroutineContext
            get() = UI + defaultCoroutineExceptionHandler
        override val network: CoroutineContext
            get() = newFixedThreadPoolContext(2, "network-context") + defaultCoroutineExceptionHandler
        override val commonPool: CoroutineContext
            get() = CommonPool + defaultCoroutineExceptionHandler

    }

    object TestCoroutineContextProvider : CoroutineContextProvider {
        override val ui: CoroutineContext
            get() = CommonPool + defaultCoroutineExceptionHandler
        override val network: CoroutineContext
            get() = CommonPool + defaultCoroutineExceptionHandler
        override val commonPool: CoroutineContext
            get() = CommonPool + defaultCoroutineExceptionHandler
    }

    object TestJDBCCoroutineContextProvider : CoroutineContextProvider {
        private val singleTreadCoroutineContext = newSingleThreadContext("Test Context")

        override val ui: CoroutineContext
            get() = singleTreadCoroutineContext + defaultCoroutineExceptionHandler
        override val network: CoroutineContext
            get() = singleTreadCoroutineContext + defaultCoroutineExceptionHandler
        override val commonPool: CoroutineContext
            get() = singleTreadCoroutineContext + defaultCoroutineExceptionHandler
    }

    companion object {
        val defaultCoroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
            // Copied from CoroutineExceptionHandler Except the first few lines to prevent infinite recursion

            // ignore CancellationException (they are normal means to terminate a coroutine)
            if (exception is CancellationException) return@CoroutineExceptionHandler
            // quit if successfully pushed exception as cancellation reason
            if (coroutineContext[Job]?.cancel(exception) == true) return@CoroutineExceptionHandler
            // otherwise just use thread's handler
            val currentThread = Thread.currentThread()
            currentThread.uncaughtExceptionHandler
        }
    }
}

