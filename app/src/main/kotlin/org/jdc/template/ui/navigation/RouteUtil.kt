package org.jdc.template.ui.navigation

@Suppress("MemberVisibilityCanBePrivate")
object RouteUtil {
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

        return entries.joinToString("&") { "${it.key}=${it.value}" }
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
}