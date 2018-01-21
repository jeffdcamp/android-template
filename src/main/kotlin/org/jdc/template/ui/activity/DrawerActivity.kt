package org.jdc.template.ui.activity

import android.support.annotation.StringRes
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import org.jdc.template.R
import org.jdc.template.ui.menu.DrawerMenu
import javax.inject.Inject

abstract class DrawerActivity : BaseActivity() {

    @Inject
    lateinit var drawerMenu: DrawerMenu

    lateinit var drawerLayout: DrawerLayout
    private var homeIsBackButton: Boolean = false

    private fun init(toolbar: Toolbar, @StringRes titleResId: Int) {
        setSupportActionBar(toolbar)
        toolbar.setTitle(titleResId)
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerMenu.setupDrawerMenu(this, drawerLayout, findViewById(R.id.nav_view))
    }

    fun setupDrawerWithBackButton(toolbar: Toolbar, @StringRes titleResId: Int) {
        homeIsBackButton = true
        init(toolbar, titleResId)

        // set the back arrow in the toolbar
        enableActionBarBackArrow(true)
    }

    fun setupDrawerWithDrawerButton(toolbar: Toolbar, @StringRes titleResId: Int) {
        homeIsBackButton = false
        init(toolbar, titleResId)
        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (homeIsBackButton) {
                finish()
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
