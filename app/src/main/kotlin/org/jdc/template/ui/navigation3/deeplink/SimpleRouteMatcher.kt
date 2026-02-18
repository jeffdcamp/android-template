package org.jdc.template.ui.navigation3.deeplink

import androidx.navigation3.runtime.NavKey
import org.jdc.template.shared.util.network.Uri

/**
 * A [RouteMatcher] for routes that carry no parameters.
 *
 * The Uri is simply `/<pathBase>` with no additional path segments or query parameters.
 * This is suitable for singleton [NavKey] objects such as `HomeRoute` or `AboutRoute`.
 *
 * ### Example
 * ```kotlin
 * @Serializable
 * object HomeRoute : NavKey
 *
 * object HomeRouteMatcher : SimpleRouteMatcher<HomeRoute>(HomeRoute, "/home".toUri())
 * ```
 *
 * @param T the specific [NavKey] type this matcher handles (typically a Kotlin `object`)
 * @param route the singleton route instance returned by [parse]
 * @param baseUri the base [Uri] that identifies this route (e.g. `"/home".toUri()`)
 * @param alternativeBaseUriList optional list of additional base [Uri]s that should also match this route
 */
open class SimpleRouteMatcher<T : NavKey>(
    private val route: T,
    baseUri: Uri,
    alternativeBaseUriList: List<Uri>? = null
) : RouteMatcher<T>(baseUri, alternativeBaseUriList) {

    override fun parse(uri: Uri): T? {
        return when {
            matchesBasePath(uri, baseUri) && uri.pathSegments.size == baseUri.pathSegments.size -> route
            alternativeBaseUriList?.any { matchesBasePath(uri, it) } == true -> route
            else -> null
        }
    }

    override fun matchesKey(route: NavKey): Boolean = route == this@SimpleRouteMatcher.route

    override fun toUri(route: T): String {
        return baseUri.toString()
    }
}
