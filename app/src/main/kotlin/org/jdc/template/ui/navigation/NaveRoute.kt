package org.jdc.template.ui.navigation

import androidx.navigation.NavController

@JvmInline
value class NavRoute(val value: String)

@JvmInline
value class NavRouteDefinition(val value: String) {
    fun asNavRoute(): NavRoute = NavRoute(value)
}

fun String.asNavRoute(): NavRoute = NavRoute(this)

fun String.asNavRouteDefinition(): NavRouteDefinition = NavRouteDefinition(this)

fun NavController.navigate(route: NavRoute) {
    navigate(route.value)
}

fun NavController.popBackStack(route: NavRoute, inclusive: Boolean, saveState: Boolean = false) {
    popBackStack(route.value, inclusive, saveState)
}

fun NavController.popBackStack(route: NavRouteDefinition, inclusive: Boolean, saveState: Boolean = false) {
    popBackStack(route.value, inclusive, saveState)
}