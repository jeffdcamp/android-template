package org.company.project.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.company.project.R;
import org.company.project.ui.menu.DrawerMenuItem;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class DrawerMenuListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<DrawerMenuListItem> menuItems;

    private static final Typeface NORMAL_TYPEFACE = Typeface.create("sans-serif-light", Typeface.NORMAL);
    private static final Typeface SELECTED_TYPEFACE = Typeface.create("sans-serif", Typeface.BOLD);

    private DrawerMenuItem selectedItem;

    static class ViewHolder {
        @InjectView(R.id.drawer_item_icon)
        ImageView image;

        @InjectView(R.id.drawer_item_text)
        TextView text;

        @Nullable
        @Optional
        @InjectView(R.id.drawer_item_bottom_line)
        View bottomLine;

        ViewHolder(@Nonnull View view) {
            ButterKnife.inject(this, view);
        }
    }

    public DrawerMenuListAdapter(Context context, List<DrawerMenuListItem> menuItems, DrawerMenuItem selectedItem) {
        inflater = LayoutInflater.from(context);
        this.menuItems = menuItems;
        this.selectedItem = selectedItem;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            DrawerMenuItemType type = DrawerMenuItemType.values()[getItemViewType(position)];
            switch (type) {
                case PRIMARY:
                    view = inflater.inflate(R.layout.drawer_primary_list_item, parent, false);
                    break;
                case SECONDARY:
                default:
                    view = inflater.inflate(R.layout.drawer_secondary_list_item, parent, false);
                    break;
            }

            if (view == null) {
                throw new IllegalStateException("null view");
            }

            holder = new ViewHolder(view);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        // set text and icon
        DrawerMenuListItem item = menuItems.get(position);
        holder.image.setImageResource(item.getIconResId());
        holder.text.setText(item.getText());

        // bold the selected primary item
        holder.text.setTypeface(selectedItem.ordinal() == item.getId() ? SELECTED_TYPEFACE : NORMAL_TYPEFACE);

        // hide the bottom line unless this is the last secondary item
        boolean lastSecondaryItem = item.getType() == DrawerMenuItemType.SECONDARY && position == (menuItems.size() - 1);
        if (holder.bottomLine != null) {
            holder.bottomLine.setVisibility(lastSecondaryItem ? View.VISIBLE : View.GONE);
        }

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return DrawerMenuItemType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        DrawerMenuListItem item = menuItems.get(position);
        return item.getType().ordinal();
    }
}