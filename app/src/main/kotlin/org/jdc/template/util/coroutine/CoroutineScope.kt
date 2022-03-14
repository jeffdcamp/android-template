@file:Suppress("unused")

package org.jdc.template.util.coroutine

import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope

/**
 * Replaces GlobalScope
 */
val ProcessScope: CoroutineScope get() = ProcessLifecycleOwner.get().lifecycleScope
