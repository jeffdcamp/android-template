package org.jdc.template.util

import kotlinx.coroutines.experimental.CommonPool
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
            get() = UI
        override val network: CoroutineContext
            get() = newFixedThreadPoolContext(2, "network-context")
        override val commonPool: CoroutineContext
            get() = CommonPool

    }

    object TestCoroutineContextProvider : CoroutineContextProvider {
        override val ui: CoroutineContext
            get() = CommonPool
        override val network: CoroutineContext
            get() = CommonPool
        override val commonPool: CoroutineContext
            get() = CommonPool
    }

    object TestJDBCCoroutineContextProvider : CoroutineContextProvider {
        private val singleTreadCoroutineContext = newSingleThreadContext("Test Context")

        override val ui: CoroutineContext
            get() = singleTreadCoroutineContext
        override val network: CoroutineContext
            get() = singleTreadCoroutineContext
        override val commonPool: CoroutineContext
            get() = singleTreadCoroutineContext
    }
}

