package org.jdc.template.ui.navigation3.navigator

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.jdc.template.ui.navigation3.navigate
import org.jdc.template.ui.navigation3.pop

/**
 * Navigator for use with a Back Stack
 *
 * Example usage (Android):
 *
 * ```kotlin
 * @Composable
 * fun MainScreen() {
 *     val backstack = rememberNavBackStack(AScreen)
 *     val navigator = DefaultNavigator(backstack)
 *
 *     val entryProvider: (NavKey) -> NavEntry<NavKey> = entryProvider {
 *         entry<ARoute> { AScreen(navigator, hiltViewModel()) }
 *         entry<BRoute> { BScreen(navigator, hiltViewModel()) }
 *         entry<CRoute> { CScreen(navigator, hiltViewModel()) }
 *     }
 *
 *     NavDisplay(
 *         backStack = backstack,
 *         onBack = { navigator.pop() },
 *         entryProvider = entryProvider,
 *     )
 * }
 * ```
 *
 * Example usage (KMP):
 *
 * ```kotlin
 * @Composable
 * fun MainScreen() {
 *     val backstack = rememberNavBackStack(NavKeyBridgeSerializer, AScreen)
 *     val navigator = DefaultNavigator(backstack)
 *
 *     val entryProvider: (NavKey) -> NavEntry<NavKey> = entryProvider {
 *         entry<ARoute> { AScreen(navigator, hiltViewModel()) }
 *         entry<BRoute> { BScreen(navigator, hiltViewModel()) }
 *         entry<CRoute> { CScreen(navigator, hiltViewModel()) }
 *     }
 *
 *     NavDisplay(
 *         backStack = backstack,
 *         onBack = { navigator.pop() },
 *         entryProvider = entryProvider,
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
class DefaultNavigator(
    private val backStack: NavBackStack<NavKey>
) : Navigation3Navigator {
    override fun navigate(key: NavKey) {
        backStack.navigate(key)
    }

    override fun navigate(keys: List<NavKey>) {
        backStack.navigate(keys)
    }

    override fun pop(): Boolean {
        return pop(null)
    }

    override fun pop(key: NavKey?): Boolean {
        return backStack.pop(key) != null
    }

    override fun popAndNavigate(key: NavKey): Boolean {
        val keyRemoved = pop()
        navigate(key)

        return keyRemoved
    }

    override fun navigateTopLevel(key: NavKey, reselected: Boolean) {
        error("navigateTopLevel() navigation not implemented in DefaultNavigator (use TopLevelBackstackNavigator instead)")
    }

    override fun getSelectedTopLevelRoute(): NavKey {
        error("getSelectedTopLevelRoute() not implemented in DefaultNavigator (use TopLevelBackstackNavigator instead)")
    }
}
