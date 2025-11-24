package org.jdc.template.ui.navigation3

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.serialization.NavBackStackSerializer
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
import kotlinx.serialization.KSerializer

/**
 * Create a navigation state that persists config changes and process death.
 * @param startRoute The initial route when the app starts.
 * @param topLevelRoutes A set of all top-level routes in the application.
 * @param navKeySerializer A serializer for `NavKey` to enable saving and restoring navigation state. NavKeySerializer() for Android OR Custom NavKeyBridgeSerializer on KMP
 */
@Composable
fun rememberNavigationState(
    startRoute: NavKey,
    topLevelRoutes: Set<NavKey>,
    navKeySerializer: KSerializer<NavKey>
): NavigationState {

    val topLevelRoute = rememberSerializable(
        startRoute, topLevelRoutes,
        serializer = MutableStateSerializer(navKeySerializer)
    ) {
        mutableStateOf(startRoute)
    }

    val backStacks = topLevelRoutes.associateWith { key -> rememberNavBackStack(navKeySerializer, key) }

    return remember(startRoute, topLevelRoutes) {
        NavigationState(
            startRoute = startRoute,
            topLevelRoute = topLevelRoute,
            backStacks = backStacks
        )
    }
}

/**
 * State holder for navigation state.
 *
 * @property startRoute - the start route. The user will exit the app through this route.
 * @param topLevelRoute - the current top level route
 * @property backStacks - the back stacks for each top level route
 */
class NavigationState(
    val startRoute: NavKey,
    topLevelRoute: MutableState<NavKey>,
    val backStacks: Map<NavKey, NavBackStack<NavKey>>
) {
    var topLevelRoute: NavKey by topLevelRoute
    val stacksInUse: List<NavKey>
        get() = if (topLevelRoute == startRoute) {
            listOf(startRoute)
        } else {
            listOf(startRoute, topLevelRoute)
        }

}

/**
 * Convert NavigationState into NavEntries.
 */
@Composable
fun NavigationState.toEntries(
    entryProvider: (NavKey) -> NavEntry<NavKey>
): SnapshotStateList<NavEntry<NavKey>> {

    val decoratedEntries = backStacks.mapValues { (_, stack) ->
        val decorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
        )
        rememberDecoratedNavEntries(
            backStack = stack,
            entryDecorators = decorators,
            entryProvider = entryProvider
        )
    }

    return stacksInUse
        .flatMap { decoratedEntries[it].orEmpty() }
        .toMutableStateList()
}

@Composable
fun rememberNavBackStack(navKeySerializer: KSerializer<NavKey>, vararg elements: NavKey): NavBackStack<NavKey> {
    return rememberSerializable(
        serializer = NavBackStackSerializer(elementSerializer = navKeySerializer)
    ) {
        NavBackStack(*elements)
    }
}
