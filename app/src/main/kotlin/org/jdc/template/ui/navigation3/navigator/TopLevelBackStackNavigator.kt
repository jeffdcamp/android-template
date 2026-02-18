package org.jdc.template.ui.navigation3.navigator

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.jdc.template.ui.navigation3.NavigationState
import org.jdc.template.ui.navigation3.navigate
import org.jdc.template.ui.navigation3.pop

/**
 * Navigator for use with BottomNavigation and Multiple Back Stacks
 *
 * Example usage (Android):
 *
 * ```kotlin
 * @Composable
 * fun MainScreen() {
 *     val navigationState = rememberNavigationState(
 *         startRoute = ARoute,
 *         topLevelRoutes = NavBarItem.entries.map { it.route }.toSet(),
 *         navKeySerializer = NavKeySerializer()
 *     )
 *
 *     val navigator = remember { TopLevelBackStackNavigator(navigationState) }
 *
 *     val entryProvider: (NavKey) -> NavEntry<NavKey> = entryProvider {
 *         entry<ARoute> { AScreen(navigator, hiltViewModel()) }
 *         entry<BRoute> { BScreen(navigator, hiltViewModel()) }
 *         entry<CRoute> { CScreen(navigator, hiltViewModel()) }
 *     }
 *
 *     val decorators: List<NavEntryDecorator<NavKey>> = listOf(
 *         rememberSaveableStateHolderNavEntryDecorator(),
 *         rememberViewModelStoreNavEntryDecorator()
 *     )
 *
 *     NavDisplay(
 *         entries = navigationState.toEntries(entryProvider, decorators),
 *         onBack = { navigator.pop() }
 *     )
 * }
 * ```
 *
 * Example usage (KMP):
 *
 * ```kotlin
 * @Composable
 * fun MainScreen() {
 *     val navigationState = rememberNavigationState(
 *         startRoute = ARoute,
 *         topLevelRoutes = NavBarItem.entries.map { it.route }.toSet(),
 *         navKeySerializer = NavKeyBridgeSerializer
 *     )
 *
 *     val navigator = remember { TopLevelBackStackNavigator(navigationState) }
 *
 *     val entryProvider: (NavKey) -> NavEntry<NavKey> = entryProvider {
 *         entry<ARoute> { AScreen(navigator, hiltViewModel()) }
 *         entry<BRoute> { BScreen(navigator, hiltViewModel()) }
 *         entry<CRoute> { CScreen(navigator, hiltViewModel()) }
 *     }
 *
 *     val decorators: List<NavEntryDecorator<NavKey>> = listOf(
 *         rememberSaveableStateHolderNavEntryDecorator(),
 *         rememberViewModelStoreNavEntryDecorator()
 *     )
 *
 *     NavDisplay(
 *         entries = navigationState.toEntries(entryProvider, decorators),
 *         onBack = { navigator.pop() }
 *     )
 * }
 *
 * val NavKeySerializerModule = SerializersModule {
 *     polymorphic(NavKey::class) {
 *         subclass(ARoute::class)
 *         subclass(BRoute::class)
 *         subclass(CRoute::class)
 *     }
 * }
 *
 * private val NavKeyJson = Json {
 *     serializersModule = NavKeySerializerModule
 *     ignoreUnknownKeys = true
 * }
 *
 * object NavKeyBridgeSerializer : KSerializer<NavKey> {
 *     override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("NavKeyBridge", PrimitiveKind.STRING)
 *
 *     override fun serialize(encoder: Encoder, value: NavKey) {
 *         val string = NavKeyJson.encodeToString(
 *             PolymorphicSerializer(NavKey::class),
 *             value
 *         )
 *         encoder.encodeString(string)
 *     }
 *
 *     override fun deserialize(decoder: Decoder): NavKey {
 *         // We read the string back and decode it using the module
 *         val string = decoder.decodeString()
 *         return NavKeyJson.decodeFromString(
 *             PolymorphicSerializer(NavKey::class),
 *             string
 *         )
 *     }
 * }
 * ```
 */
class TopLevelBackStackNavigator(val state: NavigationState) : Navigation3Navigator() {
    override fun getCurrentBackStack(): NavBackStack<NavKey>? = state.backStacks[state.topLevelRoute]

    override fun doNavigate(keys: List<NavKey>) {
        getCurrentBackStack()?.navigate(keys)
    }

    override fun doPop(key: NavKey?): Boolean {
        // If we're at the base of the current route, go back to the start route stack.
        return if (getCurrentBackStack()?.last() == state.topLevelRoute) {
            navigateTopLevel(state.startRoute, false)
            false
        } else {
            getCurrentBackStack()?.pop(key) != null
        }
    }

    override fun doNavigateTopLevel(key: NavKey, reselected: Boolean) {
        if (reselected) {
            // clear back stack
            getCurrentBackStack()?.pop(popToKey = key)
        } else {
            state.topLevelRoute = key
        }
    }

    override fun getSelectedTopLevelRoute() = state.topLevelRoute
}
