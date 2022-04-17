package org.jdc.template.ui.compose.appbar

import androidx.compose.runtime.Composable
import org.jdc.template.ui.compose.util.WindowSize

enum class AppNavBarType {
    NO_NAV, NAV_BAR, NAV_RAIL, NAV_DRAWER;

    companion object {
        fun byWindowSize(windowSize: WindowSize): AppNavBarType {
            return when(windowSize) {
                WindowSize.COMPACT -> NAV_BAR
                WindowSize.MEDIUM -> NAV_RAIL
                WindowSize.EXPANDED -> NAV_DRAWER
            }
        }
    }
}

data class AppNavBarData(
    val appNavBarType: AppNavBarType = AppNavBarType.NO_NAV,
    private val navBar: @Composable (() -> Unit)? = null,
    private val navRail: @Composable (() -> Unit)? = null,
    private val navDrawer: @Composable ((appScaffold: @Composable () -> Unit) -> Unit)? = null,
) {
    fun bottomBar(): @Composable () -> Unit {
        return if (navBar != null && appNavBarType == AppNavBarType.NAV_BAR) navBar else {{}}
    }

    fun navRail(): @Composable (() -> Unit)? {
        return if (navRail != null && appNavBarType == AppNavBarType.NAV_RAIL) navRail else null
    }

    fun navDrawer(): @Composable ((appScaffold: @Composable () -> Unit) -> Unit)? {
        return if (navDrawer != null && appNavBarType == AppNavBarType.NAV_DRAWER) navDrawer else null
    }
}