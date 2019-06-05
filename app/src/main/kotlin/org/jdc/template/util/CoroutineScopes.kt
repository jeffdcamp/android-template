package org.jdc.template.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

internal class ContextScope(context: CoroutineContext) : CoroutineScope {
    override val coroutineContext: CoroutineContext = context
}

fun ioScope(): CoroutineScope = ContextScope(SupervisorJob() + Dispatchers.IO)
fun defaultScope(): CoroutineScope = ContextScope(SupervisorJob() + Dispatchers.Default)
fun customScope(dispatcher: CoroutineDispatcher): CoroutineScope = ContextScope(SupervisorJob() + dispatcher)