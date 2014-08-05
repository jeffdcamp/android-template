package org.company.project.ui.widget;

public class DrawerMenuListItem {
    private int id;
    private String text;
    private int iconResId;
    private DrawerMenuItemType type;

    public DrawerMenuListItem(int id, String text, int iconResId, DrawerMenuItemType type) {
        this.id = id;
        this.text = text;
        this.iconResId = iconResId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getIconResId() {
        return iconResId;
    }

    public DrawerMenuItemType getType() {
        return type;
    }
}
