package org.company.project.ui.menu;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.company.project.Prefs;
import org.company.project.R;
import org.company.project.ui.AboutActivity;
import org.company.project.ui.DirectoryActivity;
import org.company.project.ui.SettingsActivity;
import org.company.project.ui.widget.DrawerMenuListAdapter;
import org.company.project.ui.widget.DrawerMenuListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DrawerMenu {
    public static final int DEFAULT_DRAWER_OPEN_GRAVITY = GravityCompat.START;

    @Inject
    Application context;

    @Inject
    Prefs prefs;

    private List<DrawerMenuListItem> navigationMenuItems;

    // locale that the menu was created in
    private String currentLocaleCode = Locale.getDefault().getISO3Language();

    public void createDrawerView(final DrawerLayout drawerLayout, ListView drawerMenuListView, final DrawerMenuListener drawerMenuItemListener) {
        final List<DrawerMenuListItem> menuItems = getNavMenuItems(false);
        drawerMenuListView.setDividerHeight(0); // remove lines by default
        drawerMenuListView.setAdapter(new DrawerMenuListAdapter(context, menuItems, getDrawerMenuItem()));

        drawerMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (drawerMenuItemListener != null) {
                    drawerMenuItemListener.onDrawerItemClick(menuItems.get(position).getId());
                }

                toggle(drawerLayout);
            }

        });
    }

    public void toggle(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(DEFAULT_DRAWER_OPEN_GRAVITY)) {
            drawerLayout.closeDrawer(DEFAULT_DRAWER_OPEN_GRAVITY);
        } else {
            drawerLayout.openDrawer(DEFAULT_DRAWER_OPEN_GRAVITY);
        }
    }

    public boolean isOpen(DrawerLayout drawerLayout) {
        return drawerLayout.isDrawerOpen(DEFAULT_DRAWER_OPEN_GRAVITY);
    }

    public boolean isOpen(Activity activity) {
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        return drawerLayout != null && isOpen(drawerLayout);
    }

    public void hideShowAllMenuItems(Activity activity, Menu menu) {
        setAllMenuItemsVisible(menu, !isOpen(activity));
    }

    public void setAllMenuItemsVisible(Menu menu, boolean visible) {
        // any menu group that is NOT 0 will be excluded (if a item is not assigned to a group, it will be hidden on drawer-open)
        menu.setGroupVisible(0, visible);
    }

    public List<DrawerMenuListItem> getNavMenuItems(boolean forceRefresh) {
        String localeCode = Locale.getDefault().getISO3Language();
        boolean localeChanged = !localeCode.equals(currentLocaleCode);
        if (localeChanged) {
            currentLocaleCode = localeCode;
        }

        if (navigationMenuItems == null || forceRefresh || localeChanged) {
            navigationMenuItems = new ArrayList<>();
            // primary items
            navigationMenuItems.add(createNavigationMenuItem(DrawerMenuItem.MAIN));
            navigationMenuItems.add(createNavigationMenuItem(DrawerMenuItem.LIBRARY));
            navigationMenuItems.add(createNavigationMenuItem(DrawerMenuItem.STORE));

            // secondary items
            navigationMenuItems.add(createNavigationMenuItem(DrawerMenuItem.SETTINGS));
            navigationMenuItems.add(createNavigationMenuItem(DrawerMenuItem.HELP));
        }

        return navigationMenuItems;
    }

    private DrawerMenuListItem createNavigationMenuItem(DrawerMenuItem menuItem) {
        return new DrawerMenuListItem(menuItem.ordinal(), menuItem.getText(context), menuItem.getIconResId(), menuItem.getMenuType());
    }

    private boolean isSameActivity(@Nonnull Activity activity, DrawerMenuItem item) {
        switch (item) {
            case MAIN:
                return activity instanceof DirectoryActivity;
            case LIBRARY:
                return activity instanceof DirectoryActivity;
            case STORE:
                return activity instanceof DirectoryActivity;
            default:
                return false;
        }
    }

    private DrawerMenuItem getDrawerMenuItem() {
        return DrawerMenuItem.MAIN; // todo change to show selected drawer item (from prefs, etc)
    }

    public void onMenuItemClick(@Nonnull Activity context, int itemId) {
        DrawerMenuItem item = DrawerMenuItem.values()[itemId];

        if (isSameActivity(context, item)) {
            return;
        }

        switch (item) {
            // Primary
            case MAIN:
                // show main activity
                context.finish();
                break;
            case LIBRARY:
                // show library activity
                context.finish();
                break;
            case STORE:
                // show store activity
                context.finish();
                break;

            // Secondary
            case SETTINGS:
                Intent settingIntent = new Intent(context, SettingsActivity.class);
                context.startActivity(settingIntent);
                break;
            case HELP:
                Intent aboutIntent = new Intent(context, AboutActivity.class);
                context.startActivity(aboutIntent);
                break;
            default:
                // do nothing
        }
    }
}
