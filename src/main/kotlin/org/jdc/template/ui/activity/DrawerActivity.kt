package org.jdc.template.ui.activity

import android.support.annotation.StringRes
import android.support.v4.view.GravityCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_directory.*
import org.jdc.template.R
import org.jdc.template.ui.menu.DrawerMenu
import javax.inject.Inject

abstract class DrawerActivity : BaseActivity() {

    @Inject
    lateinit var drawerMenu: DrawerMenu

    private var homeIsBackButton: Boolean = false

    private fun init(toolbar: Toolbar, @StringRes titleResId: Int) {
        setSupportActionBar(toolbar)
        toolbar.setTitle(titleResId)
        drawerMenu.setupDrawerMenu(this, drawer_layout, nav_view)
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
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
