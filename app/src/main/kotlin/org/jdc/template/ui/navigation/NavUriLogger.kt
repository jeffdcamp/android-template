package org.jdc.template.ui.navigation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import co.touchlab.kermit.Logger
import java.net.URLEncoder

/**
 * Used to debug navigating Route and DeepLink
 *
 * Usage: navHostFragment.navController.addOnDestinationChangedListener(NavUriLogger())
 */
@Suppress("MemberVisibilityCanBePrivate")
class NavUriLogger(val prefix: String = "") : NavController.OnDestinationChangedListener {
    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        arguments?.parcelable<Intent>(NavController.KEY_DEEP_LINK_INTENT)?.data?.let { deepLinkUri ->
            val uri = deepLinkUri.toString()

            // Find all path and query parameter placeholders
            val matches = Regex("\\{([^\\}]*)\\}").findAll(uri)
            // Create a list of the placeholder names
            val placeholderNames = matches.map { it.groupValues[1] }.toList()
            // Map placeholder names to values. Note: getString() wouldn't work when value was an enum type
            val nameValuePairs = placeholderNames.map { it to arguments[it] }

            // Replace placeholders with values
            var uriWithValues = uri
            nameValuePairs.forEach { (name, value) ->
                val oldValue = "{$name}"
                val newValue = URLEncoder.encode(value.toString(), "UTF-8")
                uriWithValues = uriWithValues.replace(oldValue, newValue)
            }

            if (uriWithValues.startsWith(ROUTE_PREFIX)) {
                val route = uriWithValues.removePrefix(ROUTE_PREFIX)
                Logger.d { "$prefix Route Navigate -> route: [$route] definition: [${destination.route}]" }
            } else {
                Logger.d { "$prefix DeepLink Navigate -> uri: [$uriWithValues] " }
            }
        }
    }

    companion object {
        const val ROUTE_PREFIX = "android-app://androidx.navigation/"
    }
}

private inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}
