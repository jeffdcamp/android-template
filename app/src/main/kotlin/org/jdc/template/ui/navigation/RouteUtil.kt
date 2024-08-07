package org.jdc.template.ui.navigation

@Suppress("MemberVisibilityCanBePrivate")
object RouteUtil {
    /**
     * Used to define a route
     *
     * Usage: navDeepLink { uriPattern = "individual/${defineArg("individualId")}" }
     */
    fun defineArg(argName: String): String {
        return "{$argName}"
    }

    /**
     * Used to define a route
     *
     * Usage: navDeepLink { uriPattern = "individual?${defineOptionalArgs("individualId","enabled")}" }
     */
    fun defineOptionalArgs(vararg argNames: String): String {
        return argNames.joinToString("&") { argName -> "$argName={$argName}" }
    }
}