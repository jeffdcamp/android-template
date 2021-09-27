package org.jdc.template.ui.menu

import android.view.MenuItem
import androidx.navigation.NavController
import dagger.hilt.android.scopes.FragmentScoped
import org.jdc.template.R
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.settings.SettingsRoute
import timber.log.Timber
import javax.inject.Inject

@FragmentScoped
class CommonMenu
@Inject constructor() {
    fun onOptionsItemSelected(navController: NavController, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_search -> true
            R.id.menu_item_settings -> {
                navController.navigate(SettingsRoute.createRoute())
                true
            }
            R.id.menu_item_about -> {
                navController.navigate(AboutRoute.createRoute())
                true
            }
            else -> {
                Timber.i("Unknown common menu item id: ${item.itemId}, ignoring")
                false
            }
        }
    }
}
