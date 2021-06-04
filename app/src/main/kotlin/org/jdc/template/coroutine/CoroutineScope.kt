@file:Suppress("unused")

package org.jdc.template.coroutine

import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope

/**
 * Replaces GlobalScope
 */
val ProcessScope: CoroutineScope get() = ProcessLifecycleOwner.get().lifecycleScope
