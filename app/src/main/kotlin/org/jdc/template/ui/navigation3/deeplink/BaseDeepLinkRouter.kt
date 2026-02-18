package org.jdc.template.ui.navigation3.deeplink

import androidx.navigation3.runtime.NavKey
import org.jdc.template.shared.util.network.Uri

/**
 * Abstract router that provides bidirectional conversion between [NavKey] route objects and
 * Uri strings using a list of [RouteMatcher] instances.
 *
 * Subclasses supply their application-specific matchers via [getMatchers]. The router iterates
 * through those matchers to serialize a [NavKey] into a Uri string ([toUri]) or to parse a
 * Uri string back into a [NavKey] ([fromUri]).
 *
 * ### Example
 * ```kotlin
 * // 1. Define routes
 * @Serializable
 * object HomeRoute : NavKey
 *
 * @Serializable
 * data class ProfileRoute(val userId: String) : NavKey
 *
 * // 2. Define matchers
 * object HomeRouteMatcher : SimpleRouteMatcher<HomeRoute>(HomeRoute, "/home".toUri())
 *
 * object ProfileRouteMatcher : RouteMatcher<ProfileRoute>("/profile".toUri()) {
 *     override fun matchesKey(route: NavKey) = route is ProfileRoute
 *
 *     override fun parse(uri: Uri): ProfileRoute? {
 *         val segmentMap = getPathSegmentMap(uri)
 *         return ProfileRoute(
 *             userId = segmentMap["userId"] ?: return null
 *         )
 *     }
 *
 *     override fun toUri(route: ProfileRoute): String {
 *         return baseUri.newBuilder()
 *             .appendPath("userId").appendPath(route.userId)
 *             .build().toString()
 *     }
 * }
 *
 * // 3. Create the router
 * object DeepLinkRouter : BaseDeepLinkRouter() {
 *     override fun getMatchers(): List<RouteMatcher<out NavKey>> = listOf(
 *         HomeRouteMatcher,
 *         ProfileRouteMatcher,
 *     )
 * }
 *
 * // 4. Usage
 * val uri = DeepLinkRouter.toUri(ProfileRoute(userId = "42"))
 * // uri == "/profile/userId/42"
 *
 * val route = DeepLinkRouter.fromUri("/profile/userId/42")
 * // route == ProfileRoute(userId = "42")
 *
 * val typedRoute: ProfileRoute? = DeepLinkRouter.fromUriAsRouteOrNull("/profile/userId/42")
 * // typedRoute == ProfileRoute(userId = "42")
 * ```
 *
 * @see RouteMatcher
 * @see SimpleRouteMatcher
 */
abstract class BaseDeepLinkRouter {

    /**
     * Returns the list of [RouteMatcher] instances that this router uses to match routes.
     *
     * Each matcher handles one specific [NavKey] type. Matchers are sorted by descending
     * path segment count so more specific routes are tried first. The first matcher whose
     * [RouteMatcher.baseUri] matches the Uri and whose [RouteMatcher.parse] returns a non-null result wins.
     *
     * @return all matchers supported by this router
     */
    abstract fun getMatchers(): List<RouteMatcher<out NavKey>>

    private val matchers: List<RouteMatcher<out NavKey>> = getMatchers().sortedByDescending { routeMatcher ->
        routeMatcher.baseUri.pathSegments.size
    }

    /**
     * Serializes the given [NavKey] into a Uri string.
     *
     * Finds the first [RouteMatcher] whose [RouteMatcher.matchesKey] returns `true` for [key]
     * and delegates to its [RouteMatcher.toUri] to produce the string.
     *
     * @param key the route to serialize
     * @return a Uri string representation of the route
     * @throws IllegalArgumentException if no registered matcher handles the given [key]
     */
    fun toUri(key: NavKey): String {
        val matcher = matchers.find { it.matchesKey(key) }
            ?: throw IllegalArgumentException("No matcher found for route: $key")

        @Suppress("UNCHECKED_CAST")
        return (matcher as RouteMatcher<NavKey>).toUri(key)
    }

    /**
     * Parses a Uri string into a [NavKey] route.
     *
     * Iterates through the registered matchers (most specific first) and returns the result
     * from the first matcher whose [RouteMatcher.baseUri] matches the Uri and whose
     * [RouteMatcher.parse] returns a non-null value.
     *
     * @param uriString the Uri string to parse (e.g. `"/profile/userId/42"`)
     * @return the parsed [NavKey], or `null` if no matcher recognizes the Uri
     */
    fun fromUri(uriString: String): NavKey? {
        val uri = Uri.parse(uriString)
        matchers.forEach { matcher ->
            if (matcher.matchesUri(uri)) {
                val route = matcher.parse(uri)
                if (route != null) return route
            }
        }

        return null
    }

    /**
     * Parses a Uri string and returns the result cast to the specified [NavKey] subtype [T],
     * or `null` if parsing fails or the result is not of type [T].
     *
     * This is a convenience wrapper around [fromUri] that avoids manual casting at the call site.
     *
     * @param T the expected [NavKey] subtype
     * @param uriString the Uri string to parse
     * @return the parsed route as [T], or `null` if the Uri is unrecognized or the result is
     *         not an instance of [T]
     */
    inline fun <reified T : NavKey> fromUriAsRouteOrNull(uriString: String): T? {
        val route = fromUri(uriString) ?: return null
        return route as? T
    }
}
