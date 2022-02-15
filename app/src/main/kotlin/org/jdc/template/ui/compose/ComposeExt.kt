package org.jdc.template.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Used to grant access to the Nav Controller inside compose functions
 *
 * This must be set in the fragment or where the nav controller is created as follows
 *
 * CompositionLocalProvider(LocalNavController provides navController) {
 *     // Rest of compose code
 *     // LocalNavController.value will be present.
 * }
 */
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
inline fun <T : R, R> Flow<T>.collectAsLifecycleState(
    initial: R,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val rememberedFlow = remember(this, lifecycleOwner) {
        this.flowWithLifecycle(lifecycleOwner.lifecycle, state)
    }
    return rememberedFlow.collectAsState(initial, context)
}