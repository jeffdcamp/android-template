package org.jdc.template.ui.navigation

import java.net.URLEncoder

@Suppress("MemberVisibilityCanBePrivate")
object RouteUtil {
    const val DEFAULT_STRING_LIST_DELIMITER = "\u241E"

    /**
     * Used to define a route
     *
     * Usage: fragment<T>("individual/${defineArg("individualId")}") { ... }
     */
    fun defineArg(argName: String): String {
        return "{$argName}"
    }

    /**
     * Used to define a route
     *
     * Usage: fragment<T>("individual?${defineOptionalArgs("individualId","enabled")}") { ... }
     */
    fun defineOptionalArgs(vararg argNames: String): String {
        return argNames.joinToString("&") { argName -> "${argName}={${argName}}" }
    }

    /**
     * Adds arguments to route for navigation calls
     *
     * Usage: val route = "individual$RouteUtil.optionalArgs(mapOf("individualId" to "1234","enabled" to true))"
     */
    fun optionalArgs(argNameValues: Map<String, Any?>, excludeNullValues: Boolean = true): String {
        val entries = if (excludeNullValues) {
            argNameValues.entries.filter { it.value != null }
        } else {
            argNameValues.entries
        }

        return entries.joinToString("&") {
            val value = when (val v = it.value) {
                is String -> encodeArg(v)
                else -> v
            }

            "${it.key}=${value}"
        }
    }

    /**
     * Adds arguments to route for navigation calls
     *
     * Usage: val route = "individual$RouteUtil.optionalArgs(mapOf("individualId" to "1234","enabled" to true))"
     */
    fun optionalArgs(argNameValues: List<Pair<String, Any?>>, excludeNullValues: Boolean = true): String {
        return optionalArgs(argNameValues.associate { it }, excludeNullValues)
    }

    /**
     * Adds arguments to route for navigation calls
     *
     * Usage: val route = "individual$RouteUtil.optionalArgs(mapOf("individualId" to "1234","enabled" to true))"
     */
    fun optionalArgs(vararg argNameValues: Pair<String, Any?>, excludeNullValues: Boolean = true): String {
        return optionalArgs(argNameValues.toList(), excludeNullValues)
    }

    /**
     * URLEncoder String argument
     */
    fun encodeArg(value: String?): String? = value?.let { URLEncoder.encode(it, "utf-8") }

    /**
     * Used to transform List<String> to a String to be used with ListNavType.StringListType
     */
    fun listToString(list: List<String>?, separator: String = DEFAULT_STRING_LIST_DELIMITER): String? {
        return list?.map { encodeArg(it) }?.joinToString(separator = separator)
    }

    /**
     * Used to transform List<Int> to a String to be used with ListNavType.StringListType
     */
    fun listToString(list: List<Number>?): String? {
        return list?.joinToString(separator = DEFAULT_STRING_LIST_DELIMITER)
    }
}