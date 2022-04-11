package org.jdc.template.ui.compose.appbar

import androidx.compose.runtime.Composable

data class AppNavBarData(
    val navBarAsRail: Boolean = false,
    private val navBar: @Composable (() -> Unit)? = null,
    private val navRail: @Composable (() -> Unit)? = null
) {
    fun getBottomBar(): @Composable () -> Unit {
        return if (navBar != null && !navBarAsRail) navBar else {{}}
    }

    fun getNavRail(): @Composable (() -> Unit)? {
        return navRail
    }

    fun isNavRail(): Boolean = navBarAsRail && navRail != null
}