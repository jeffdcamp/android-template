package org.jdc.template.ui.navigation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import co.touchlab.kermit.Logger

/**
 * Used to debug navigating Route and DeepLink
 *
 * Usage: navController.addOnDestinationChangedListener(NavUriLogger())
 */
@Suppress("MemberVisibilityCanBePrivate")
class NavUriLogger(val prefix: String = "") : NavController.OnDestinationChangedListener {
    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        arguments?.parcelable<Intent>(NavController.KEY_DEEP_LINK_INTENT)?.data?.let { deepLinkUri ->
            val uri = deepLinkUri.toString()
            if (uri.startsWith(ROUTE_PREFIX)) {
                val route = uri.removePrefix(ROUTE_PREFIX)
                Logger.d { "$prefix Route Navigate -> route: [$route] definition: [${destination.route}]" }
            } else {
                Logger.d { "$prefix DeepLink Navigate -> uri: [$uri] " }
            }
        }
    }

    companion object {
        const val ROUTE_PREFIX = "android-app://androidx.navigation/"
    }
}

private inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}