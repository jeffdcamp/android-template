package org.jdc.template.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

internal class ContextScope(context: CoroutineContext) : CoroutineScope {
    override val coroutineContext: CoroutineContext = context
}

fun IOScope(): CoroutineScope = ContextScope(SupervisorJob() + Dispatchers.IO)
fun DefaultScope(): CoroutineScope = ContextScope(SupervisorJob() + Dispatchers.Default)
fun CustomScope(dispatcher: CoroutineDispatcher): CoroutineScope = ContextScope(SupervisorJob() + dispatcher)