package org.company.project.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.company.project.R;
import org.company.project.domain.main.individual.Individual;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DirectoryAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    static class ViewHolder {
        @Bind(R.id.text1)
        TextView text1;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public DirectoryAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_dual_pane, parent, false);

        ViewHolder holder = new ViewHolder(view);
        if (view != null) {
            view.setTag(holder);
        }

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        Individual individual = new Individual(cursor);
        holder.text1.setText(individual.getFullName());
    }
}
