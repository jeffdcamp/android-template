package org.company.project.menu;

import android.content.Context;

import org.company.project.R;
import org.company.project.widget.DrawerMenuItemType;

import javax.annotation.Nonnull;

public enum DrawerMenuItem {
    MAIN(DrawerMenuItemType.PRIMARY, R.string.nav_main, R.drawable.ic_action_about),
    LIBRARY(DrawerMenuItemType.PRIMARY, R.string.nav_my_library, R.drawable.ic_action_about),
    STORE(DrawerMenuItemType.PRIMARY, R.string.nav_store, R.drawable.ic_action_about),

    SETTINGS(DrawerMenuItemType.SECONDARY, R.string.menu_settings, R.drawable.ic_action_settings),
    HELP(DrawerMenuItemType.SECONDARY, R.string.menu_help, R.drawable.ic_action_about);

    private int textResId;
    private int iconResId;
    private DrawerMenuItemType menuType;

    private DrawerMenuItem(DrawerMenuItemType menuType, int textResId, int iconResId) {
        this.textResId = textResId;
        this.iconResId = iconResId;
        this.menuType = menuType;
    }

    public String getText(@Nonnull Context context) {
        return context.getString(textResId);
    }

    public int getIconResId() {
        return iconResId;
    }

    public DrawerMenuItemType getMenuType() {
        return menuType;
    }
}
