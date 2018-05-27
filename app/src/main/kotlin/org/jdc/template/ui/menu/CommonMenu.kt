package org.jdc.template.ui.menu

import android.view.MenuItem
import androidx.navigation.NavController
import org.jdc.template.MainNavigationDirections
import org.jdc.template.R
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonMenu
@Inject constructor() {

    fun onOptionsItemSelected(navController: NavController, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_search -> true
            R.id.menu_item_settings -> {
                navController.navigate(MainNavigationDirections.settings())
                true
            }
            R.id.menu_item_about -> {
                navController.navigate(MainNavigationDirections.about())
                true
            }
            else -> {
                Timber.i("Unknown common menu item id: ${item.itemId}, ignoring")
                false
            }
        }
    }
}
