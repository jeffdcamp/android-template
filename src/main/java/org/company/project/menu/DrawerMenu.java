package org.company.project.menu;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.company.project.ForApplication;
import org.company.project.R;
import org.company.project.activity.MainActivity;
import org.company.project.widget.DrawerMenuListAdapter;
import org.company.project.widget.DrawerMenuListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * User: jcampbell
 * Date: 5/17/13
 */
@Singleton
public class DrawerMenu {
    public static final int DEFAULT_DRAWER_OPEN_GRAVITY = GravityCompat.START;

    @Inject
    @ForApplication
    Context context;

    private List<DrawerMenuListItem> navigationMenuItems;

    // locale that the menu was created in
    private String currentLocaleCode = Locale.getDefault().getISO3Language();

    public void createDrawerView(final DrawerLayout drawerLayout, ListView drawerMenuListView, final DrawerMenuListener drawerMenuItemListener) {
        final List<DrawerMenuListItem> menuItems = getNavMenuItems(false);
        drawerMenuListView.setAdapter(new DrawerMenuListAdapter(context, menuItems));

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
        menu.setGroupVisible(0, visible);  // any menu group that is NOT 0 will be excluded (if a item is not assigned to a group, it will be hidden on drawer-open)
    }

    public List<DrawerMenuListItem> getNavMenuItems(boolean forceRefresh) {
        String localeCode = Locale.getDefault().getISO3Language();
        boolean localeChanged = !localeCode.equals(currentLocaleCode);
        if (localeChanged) {
            currentLocaleCode = localeCode;
        }

        if (navigationMenuItems == null || forceRefresh || localeChanged) {
            navigationMenuItems = new ArrayList<DrawerMenuListItem>();
            navigationMenuItems.add(createNavigationMenuItem(DrawerMenuItem.MAIN));
            navigationMenuItems.add(createNavigationMenuItem(DrawerMenuItem.MY_LIBRARY));
            navigationMenuItems.add(createNavigationMenuItem(DrawerMenuItem.STORE));
        }

        return navigationMenuItems;
    }

    private DrawerMenuListItem createNavigationMenuItem(DrawerMenuItem type) {
        DrawerMenuListItem item = new DrawerMenuListItem();

        item.setId(type.ordinal());
        item.setText(context.getString(type.getTextResID()));
        item.setIconResID(type.getIconResID());

        return item;
    }

    private boolean isSameActivity(Activity context, DrawerMenuItem item) {
        switch (item) {
            case MAIN:
                return context instanceof MainActivity;
            case MY_LIBRARY:
                return context instanceof MainActivity;
            case STORE:
                return context instanceof MainActivity;
        }

        return false;
    }

    public void onMenuItemClick(Activity context, int itemId) {
        DrawerMenuItem item = DrawerMenuItem.values()[itemId];

        if (isSameActivity(context, item)) {
            return;
        }

        switch (item) {
            case MAIN:
                // do stuff
                break;
            case MY_LIBRARY:
                // do stuff
                break;
            case STORE:
                // do stuff
                break;
        }

        context.finish();
    }
}
