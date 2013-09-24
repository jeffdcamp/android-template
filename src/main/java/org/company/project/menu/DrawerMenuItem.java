package org.company.project.menu;


import org.company.project.R;

public enum DrawerMenuItem {
    MAIN(R.string.nav_main, R.drawable.ic_action_about),
    MY_LIBRARY(R.string.nav_my_library, R.drawable.ic_action_about),
    STORE(R.string.nav_store, R.drawable.ic_action_about);

    private int textResID;
    private int iconResID;

    private DrawerMenuItem(int textResID, int iconResID) {
        this.textResID = textResID;
        this.iconResID = iconResID;
    }

    public int getTextResID() {
        return textResID;
    }

    public int getIconResID() {
        return iconResID;
    }
}
