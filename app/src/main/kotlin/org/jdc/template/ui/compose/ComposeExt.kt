package org.jdc.template.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow

val LocalNavController = staticCompositionLocalOf<NavController?> { null }

/**
 * Simplify doing flowWithLifecycle as suggested in "A safer way to collect flows from Android UIs"
 * https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda
 *
 * NOTE:
 * - One scenario that you might not need to use this is if your collecting on a MutableStateFlow from your ViewModel
 */
@Suppress("NOTHING_TO_INLINE")
@Composable
inline fun <T> Flow<T>.toLifecycleFlow(state: Lifecycle.State = Lifecycle.State.STARTED): Flow<T> {
    val lifecycleOwner = LocalLifecycleOwner.current
    return remember(this, lifecycleOwner) {
        this.flowWithLifecycle(lifecycleOwner.lifecycle, state)
    }
}