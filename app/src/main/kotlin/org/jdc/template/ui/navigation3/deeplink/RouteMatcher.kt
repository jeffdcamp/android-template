package org.jdc.template.ui.navigation3.deeplink

import androidx.navigation3.runtime.NavKey
import io.ktor.http.decodeURLQueryComponent
import io.ktor.http.encodeURLQueryComponent
import org.jdc.template.shared.util.network.Uri
import org.jdc.template.ui.navigation3.deeplink.RouteMatcher.Companion.DEFAULT_STRING_LIST_DELIMITER

/**
 * Abstract base for bidirectional mapping between a [NavKey] route and a Uri string.
 *
 * Each concrete matcher knows how to:
 * 1. **Parse** an incoming [Uri] into a typed [NavKey] (or return `null` if it does not match).
 * 2. **Serialize** a typed [NavKey] back into a Uri string.
 * 3. **Identify** whether a given [NavKey] belongs to this matcher.
 *
 * All matchers are registered in [BaseDeepLinkRouter], which iterates through them to
 * convert between Uri strings and route objects used by the navigation framework.
 *
 * ### Simple routes
 * Routes that carry no parameters can use [SimpleRouteMatcher]:
 * ```kotlin
 * @Serializable
 * object HomeRoute : NavKey
 *
 * object HomeRouteMatcher : SimpleRouteMatcher<HomeRoute>(HomeRoute, "/home".toUri())
 * ```
 *
 * ### Parameterized routes
 * Routes with arguments extend [RouteMatcher] directly, placing required values in
 * path segments (via [getPathSegmentMap]) and optional values in query parameters:
 * ```kotlin
 * @Serializable
 * data class ImageViewerRoute(
 *     val imageAssetId: ImageAssetId,
 *     val title: String? = null
 * ) : NavKey
 *
 * object ImageViewerRouteMatcher : RouteMatcher<ImageViewerRoute>("/imageViewer".toUri()) {
 *     override fun matchesKey(route: NavKey) = route is ImageViewerRoute
 *
 *     override fun parse(uri: Uri): ImageViewerRoute? {
 *         val segmentMap = getPathSegmentMap(uri)
 *         return ImageViewerRoute(
 *             imageAssetId = segmentMap[Arg.IMAGE_ASSET_ID]?.let { ImageAssetId(it) } ?: return null,
 *             title = uri.getQueryParameter(Arg.TITLE)
 *         )
 *     }
 *
 *     override fun toUri(route: ImageViewerRoute): String {
 *         return baseUri.newBuilder()
 *             .appendPath(Arg.IMAGE_ASSET_ID).appendPath(route.imageAssetId.value)
 *             .appendQueryParameter(Arg.TITLE, route.title)
 *             .build().toString()
 *     }
 * }
 * ```
 *
 * @param T the specific [NavKey] type this matcher handles
 * @property baseUri the base [Uri] used to identify Uris belonging to this route
 *                 (e.g. `Uri.parse("/home")`, `Uri.parse("/home/edit")`, `Uri.parse("/imageViewer")`)
 * @property alternativeBaseUriList optional list of additional base [Uri]s that should also match this route
 */
abstract class RouteMatcher<T : NavKey>(
    val baseUri: Uri,
    val alternativeBaseUriList: List<Uri>? = null,
) {
    /**
     * Checks whether the given [uri] matches this matcher's [baseUri] or any of the
     * [alternativeBaseUriList] entries.
     *
     * A match requires the Uri's path to start with the base Uri's path.
     *
     * @param uri the [Uri] to test
     * @return `true` if the Uri matches this matcher's base path
     */
    fun matchesUri(uri: Uri): Boolean {
        return matchesBasePath(uri, baseUri) ||
            alternativeBaseUriList?.any { matchesBasePath(uri, it) } == true
    }

    protected fun matchesBasePath(uri: Uri, baseUri: Uri): Boolean {
        return uri.path.startsWith(baseUri.path)
    }

    /**
     * Attempts to parse the given [uri] into a [T] route instance.
     *
     * @param uri the incoming Uri to parse
     * @return a [T] instance populated from the Uri's path segments and query parameters,
     *         or `null` if the Uri does not match this route's pattern
     */
    abstract fun parse(uri: Uri): T?

    /**
     * Checks whether the given [route] is the type handled by this matcher.
     *
     * Used by [BaseDeepLinkRouter.toUri] to find the correct matcher for serialization.
     *
     * @param route the [NavKey] to check
     * @return `true` if this matcher can serialize the given route
     */
    abstract fun matchesKey(route: NavKey): Boolean

    /**
     * Serializes the given [route] into a Uri string.
     *
     * The returned string can later be fed back through [parse] to reconstruct
     * an equivalent route instance.
     *
     * @param route the route to serialize
     * @return a Uri string representation of the route
     */
    abstract fun toUri(route: T): String

    /**
     * Extracts key-value pairs from the path segments of the given [uri].
     *
     * Path segments are expected to follow a `/pathBase/key1/value1/key2/value2/…` convention.
     * The first segment (index 0) is skipped because it is the [baseUri] path. Subsequent segments
     * alternate between keys (odd indices) and values (even indices).
     *
     * @param uri the Uri whose path segments to parse
     * @return a map of key-value pairs extracted from the path segments
     */
    fun getPathSegmentMap(uri: Uri): Map<String, String> {
        val segmentMap = mutableMapOf<String, String>()
        var lastKey: String? = null

        uri.pathSegments.forEachIndexed { index, segmentText ->
            when {
                index == 0 -> {

                }
                index % 2 != 0 -> {
                    lastKey = segmentText
                }
                else -> {
                    if (lastKey != null) {
                        segmentMap[lastKey] = segmentText
                    }

                    lastKey = null
                }
            }
        }

        return segmentMap
    }

    /**
     * URL-encodes a string argument so it can be safely embedded in a Uri.
     *
     * @param value the string to encode, or `null`
     * @param legacy when `true`, spaces are encoded as `+` instead of `%20` to stay
     *               consistent with values previously encoded by [java.net.URLEncoder.encode]
     * @return the encoded string, or `null` if [value] is `null`
     */
    fun encodeArg(value: String?, legacy: Boolean = false): String? {
        return value?.encodeURLQueryComponent(spaceToPlus = legacy) // spaceToPlus is only used to be consistent previously encoded string by java URLEncoder.encode()
    }

    /**
     * URL-decodes a previously encoded string argument.
     *
     * @param value the string to decode, or `null`
     * @param legacy when `true`, treats `+` as a space to support values previously encoded
     *               by [java.net.URLEncoder.encode]
     * @return the decoded string, or `null` if [value] is `null`
     */
    fun decodeArg(value: String?, legacy: Boolean = false): String? {
        return value?.decodeURLQueryComponent(plusIsSpace = legacy) // plusIsSpace is only used to support previously encoded string by java URLEncoder.encode()
    }

    /**
     * Encodes and joins a list of strings into a single delimited string.
     *
     * Each element is URL-encoded via [encodeArg] before joining. Use [encodedListToStringList]
     * to reverse this operation.
     *
     * @param list the strings to join, or `null`
     * @param separator the delimiter placed between encoded elements
     *                  (defaults to [DEFAULT_STRING_LIST_DELIMITER])
     * @return the joined string, or `null` if [list] is `null` or empty
     */
    fun listToString(list: List<String>?, separator: String = DEFAULT_STRING_LIST_DELIMITER): String? {
        if (list.isNullOrEmpty()) {
            return null
        }

        return list.map { encodeArg(it) }.joinToString(separator = separator)
    }

    /**
     * Joins a list of numbers into a single delimited string.
     *
     * @param list the numbers to join, or `null`
     * @return the joined string, or `null` if [list] is `null` or empty
     */
    fun listToString(list: List<Number>?): String? {
        if (list.isNullOrEmpty()) {
            return null
        }

        return list.joinToString(separator = DEFAULT_STRING_LIST_DELIMITER)
    }

    /**
     * Splits and decodes a delimited string previously created by [listToString] back into a
     * list of strings.
     *
     * @param encodedList the delimited and encoded string, or `null`
     * @return the decoded list of strings, or `null` if [encodedList] is `null`
     */
    fun encodedListToStringList(encodedList: String?): List<String>? = encodedList?.split(DEFAULT_STRING_LIST_DELIMITER)?.mapNotNull { decodeArg(it.trim()) }

    companion object {
        /** Delimiter used by [listToString] and [encodedListToStringList] (Unicode Record Separator U+241E). */
        const val DEFAULT_STRING_LIST_DELIMITER = "\u241E" // ASCII record separator character
    }
}

