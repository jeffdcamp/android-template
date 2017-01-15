package org.jdc.template.ui.menu

import android.app.Activity
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import org.jdc.template.InternalIntents
import org.jdc.template.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrawerMenu
@Inject
constructor() {

    @Inject
    lateinit var internalIntents: InternalIntents

    fun setupDrawerMenu(parentActivity: Activity, drawerLayout: DrawerLayout, navigationView: NavigationView) {

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            onDrawerItemSelected(menuItem, parentActivity)
            drawerLayout.closeDrawers()
            true
        }
    }

    fun onDrawerItemSelected(item: MenuItem, parentActivity: Activity): Boolean {
        when (item.itemId) {
            R.id.menu_drawer_item_settings -> {
                internalIntents.showSettings(parentActivity)
                return true
            }
            R.id.menu_drawer_item_help -> {
                internalIntents.showHelp(parentActivity)
                return true
            }
        }

        return false
    }
}
