package org.company.project.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.company.project.R;
import org.company.project.menu.DrawerMenu;
import org.company.project.menu.DrawerMenuListener;

import javax.inject.Inject;

public class DrawerActivity extends ActionBarActivity implements DrawerMenuListener {

    @Inject
    DrawerMenu drawerMenu;

    private boolean showDrawerIcon = false;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    public void setupDrawer(boolean showDrawerIcon, final int titleResID, final boolean hasListNavigationMode) {
        setupDrawer(showDrawerIcon, getString(titleResID), hasListNavigationMode);
    }

    public void setupDrawer(boolean showDrawerIcon, final String title, final boolean hasListNavigationMode) {
        this.showDrawerIcon = showDrawerIcon;

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerMenu.createDrawerView(drawerLayout, (ListView) findViewById(R.id.drawer_menu_items), this);

        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_navigation_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                toggleDrawer(false, getSupportActionBar(), title, hasListNavigationMode);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                toggleDrawer(true, getSupportActionBar(), getString(R.string.app_name), hasListNavigationMode);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public void onDrawerItemClick(int itemId) {
        drawerMenu.onMenuItemClick(this, itemId);
    }

    // Called whenever we call invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        drawerMenu.hideShowAllMenuItems(this, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void toggleDrawer(boolean drawerOpen, ActionBar actionBar, String title, boolean hasListNavigationMode) {
        if (hasListNavigationMode) {
            actionBar.setNavigationMode(drawerOpen ? ActionBar.NAVIGATION_MODE_STANDARD : ActionBar.NAVIGATION_MODE_LIST);
            actionBar.setDisplayShowTitleEnabled(drawerOpen);
        }

        actionBar.setTitle(title != null ? title : getString(R.string.app_name));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (showDrawerIcon) {
            // Sync the toggle state after onRestoreInstanceState has occurred.
            drawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
