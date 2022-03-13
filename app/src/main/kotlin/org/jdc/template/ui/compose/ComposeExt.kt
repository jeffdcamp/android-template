package org.jdc.template.ui.compose

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

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
