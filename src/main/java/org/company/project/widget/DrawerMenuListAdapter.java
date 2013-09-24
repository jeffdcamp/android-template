package org.company.project.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.company.project.R;

import java.util.List;

public class DrawerMenuListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<DrawerMenuListItem> menuItems;

    // CHECKSTYLE:OFF -- ViewHolder pattern (public variables)
    class ViewHolder {
        public TextView text;
        public ImageView image;
    }
    // CHECKSTYLE:ON

    public DrawerMenuListAdapter(Context context, List<DrawerMenuListItem> menuItems) {
        inflater = LayoutInflater.from(context);
        this.menuItems = menuItems;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null || convertView instanceof TextView) {
            convertView = inflater.inflate(R.layout.drawer_list_item, null);

            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.rbm_item_icon);
            holder.text = (TextView) convertView.findViewById(R.id.rbm_item_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.image.setImageResource(menuItems.get(position).getIconResID());
        holder.text.setText(menuItems.get(position).getText());

        return convertView;
    }
}