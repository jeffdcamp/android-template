package org.jdc.template.ui.menu

import android.view.MenuItem
import androidx.navigation.NavController
import dagger.hilt.android.scopes.FragmentScoped
import org.jdc.template.MainNavigationDirections
import org.jdc.template.R
import timber.log.Timber
import javax.inject.Inject

@FragmentScoped
class CommonMenu
@Inject constructor() {
    fun onOptionsItemSelected(navController: NavController, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_search -> true
            R.id.menu_item_settings -> {
                navController.navigate(MainNavigationDirections.actionGlobalSettingsFragment())
                true
            }
            R.id.menu_item_about -> {
                navController.navigate(MainNavigationDirections.actionGlobalAboutFragment())
                true
            }
            else -> {
                Timber.i("Unknown common menu item id: ${item.itemId}, ignoring")
                false
            }
        }
    }
}
