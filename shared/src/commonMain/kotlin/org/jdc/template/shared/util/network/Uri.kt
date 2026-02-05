package org.jdc.template.shared.util.network

import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.Url
import io.ktor.http.appendPathSegments
import io.ktor.http.encodedPath
import io.ktor.http.formUrlEncode

/**
 * A KMP-friendly URI wrapper backed by Ktor's URLBuilder.
 * Supports both absolute URLs (https://example.com) and relative URIs (/path?query).
 */
class Uri private constructor(
    private val delegate: Url,
    private val isRelative: Boolean
) {
    /**
     * The encoded path component.
     */
    val path: String get() = delegate.encodedPath

    /**
     * The scheme (for example, `https`) or `null` for relative URIs.
     */
    val scheme: String? get() = if (isRelative) null else delegate.protocol.name

    /**
     * The host or `null` for relative URIs.
     */
    val host: String? get() = if (isRelative) null else delegate.host

    /**
     * The port number.
     */
    val port: Int get() = delegate.port

    /**
     * The fragment component (without the `#` prefix).
     */
    val fragment: String get() = delegate.fragment

    /**
     * Path segments split on `/`, excluding empty segments.
     *
     * Mirrors Android's `Uri.pathSegments` behavior.
     */
    val pathSegments: List<String>get() = path.trimStart('/').split('/').filter { it.isNotEmpty() }

    /**
     * Query parameters.
     */
    val parameters: Parameters get() = delegate.parameters

    /**
     * Returns the first value for the given query parameter key, or `null` if absent.
     *
     * @param key The query parameter key to look up.
     * @return The first value for [key], or `null` if missing.
     */
    fun getQueryParameter(key: String): String? = parameters[key]

    /**
     * Returns all values for the given query parameter key, or `null` if absent.
     *
     * @param key The query parameter key to look up.
     * @return All values for [key], or `null` if missing.
     */
    fun getQueryParameters(key: String): List<String>? = parameters.getAll(key)

    /**
     * Creates a mutable Builder initialized with this Uri's state.
     *
     * @return A new [Builder] seeded with this Uri's values.
     */
    fun newBuilder(): Builder {
        return Builder(URLBuilder(delegate), isRelative)
    }

    override fun toString(): String {
        return if (isRelative) {
            buildString {
                append(delegate.encodedPath)
                if (!delegate.parameters.isEmpty()) {
                    append("?")
                    append(delegate.parameters.formUrlEncode())
                }
                if (delegate.fragment.isNotEmpty()) {
                    append("#")
                    append(delegate.fragment)
                }
            }
        } else {
            delegate.toString()
        }
    }

    companion object {
        private const val DUMMY_BASE = "https://dummy"

        /**
         * Parses the given string into a [Uri].
         *
         * Supports absolute URLs (containing `://`) and relative URIs. Relative
         * URIs are parsed against an internal dummy base and then treated as relative.
         *
         * @param uriString The string to parse.
         * @return The parsed [Uri], or a relative empty [Uri] if parsing fails.
         */
        fun parse(uriString: String): Uri {
            val isRelative = !uriString.contains("://")
            val parseTarget = if (isRelative) "$DUMMY_BASE$uriString" else uriString

            return try {
                Uri(Url(parseTarget), isRelative)
            } catch (ignore: Exception) {
                // Fallback for empty or extremely malformed strings
                Uri(Url(DUMMY_BASE), true)
            }
        }
    }

    /**
     * A Mutable Builder for KmpUri.
     */
    class Builder internal constructor(
        private val urlBuilder: URLBuilder,
        private var isRelative: Boolean
    ) {
        /**
         * Creates a builder initialized to a relative URI.
         */
        constructor() : this(URLBuilder(DUMMY_BASE), true)

        /**
         * Sets the scheme and marks the URI as absolute.
         *
         * @param scheme The URI scheme (for example, `https`).
         * @return This [Builder] for chaining.
         */
        fun scheme(scheme: String) = apply {
            urlBuilder.protocol = URLProtocol.Companion.createOrDefault(scheme)
            isRelative = false // If you set a scheme, it's no longer relative
        }

        /**
         * Sets the host and marks the URI as absolute.
         *
         * @param host The host name.
         * @return This [Builder] for chaining.
         */
        fun host(host: String) = apply {
            urlBuilder.host = host
            isRelative = false // If you set a host, it implies absolute
        }

        /**
         * Replaces the encoded path.
         *
         * @param path The encoded path to set.
         * @return This [Builder] for chaining.
         */
        fun path(path: String) = apply {
            urlBuilder.encodedPath = path
        }

        /**
         * Appends a single path segment.
         *
         * @param segment The path segment to append.
         * @return This [Builder] for chaining.
         */
        fun appendPath(segment: String) = apply {
            urlBuilder.appendPathSegments(segment)
        }

        /**
         * Removes all query parameters.
         *
         * @return This [Builder] for chaining.
         */
        fun clearQuery() = apply {
            urlBuilder.parameters.clear()
        }

        /**
         * Appends a query parameter if [value] is non-null.
         *
         * @param key The query parameter key.
         * @param value The value to append; ignored if `null`.
         * @return This [Builder] for chaining.
         */
        fun appendQueryParameter(key: String, value: String?) = apply {
            if (value != null) {
                urlBuilder.parameters.append(key, value)
            }
        }

        /**
         * Sets or removes a query parameter.
         *
         * If [value] is non-null, replaces existing values with a single value.
         * If [value] is null, removes the parameter.
         *
         * @param key The query parameter key.
         * @param value The value to set, or `null` to remove.
         * @return This [Builder] for chaining.
         */
        fun setQueryParameter(key: String, value: String?) = apply {
            if (value != null) {
                urlBuilder.parameters[key] = value // Replaces existing
            } else {
                urlBuilder.parameters.remove(key)
            }
        }

        /**
         * Sets the fragment (without the `#` prefix).
         *
         * @param fragment The fragment value.
         * @return This [Builder] for chaining.
         */
        fun fragment(fragment: String) = apply {
            urlBuilder.fragment = fragment
        }

        /**
         * Builds an immutable [Uri] from the current builder state.
         *
         * @return The built [Uri].
         */
        fun build(): Uri {
            return Uri(urlBuilder.build(), isRelative)
        }
    }
}