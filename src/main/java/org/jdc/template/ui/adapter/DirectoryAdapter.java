package org.jdc.template.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devbrackets.android.recyclerext.adapter.RecyclerListAdapter;

import org.jdc.template.R;
import org.jdc.template.event.DirectoryItemClickedEvent;
import org.jdc.template.inject.Injector;
import org.jdc.template.model.database.main.individual.Individual;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pocketbus.Bus;

public class DirectoryAdapter extends RecyclerListAdapter<DirectoryAdapter.ViewHolder, Individual> {
    @Inject
    Bus bus;

    private LayoutInflater inflater;

    // dual pane variables
    private boolean dualPane = false;
    private long lastSelectedItemId = 0;
    private ViewHolder lastSelectedViewHolder;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text1)
        TextView text1TextView;
        @BindView(R.id.list_item)
        View listItemView;

        // dual pane items
        @BindView(R.id.list_item_full_layout)
        View listItemFullLayout;
        @BindView(R.id.listview_sidebar_selected)
        View sideBarSelectedView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setText(String text) {
            text1TextView.setText(text);
        }

        public void setItemSelected(boolean selected) {
            listItemFullLayout.setSelected(selected);
            sideBarSelectedView.setVisibility(selected ? View.VISIBLE : View.GONE);
        }
    }

    public DirectoryAdapter(Context context, boolean dualPane) {
        Injector.get().inject(this);

        this.inflater = LayoutInflater.from(context);
        this.dualPane = dualPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_item_dual_pane, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Individual individual = getItem(position);
        final long itemId = individual.getId();

        // bind data to view holder
        holder.text1TextView.setText(individual.getFullName());
        if (dualPane) {
            holder.setItemSelected(itemId == lastSelectedItemId);
        }

        // Click listener
        holder.listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked(holder, itemId);
            }
        });
    }

    private void onItemClicked(ViewHolder holder, long selectedItemId) {
        this.lastSelectedItemId = selectedItemId;
        toggleDualPaneSelection(holder);

        bus.post(new DirectoryItemClickedEvent(selectedItemId));
    }

    // dual pane methods
    private void toggleDualPaneSelection(ViewHolder holder) {
        if (dualPane) {
            if (lastSelectedViewHolder != null) {
                lastSelectedViewHolder.setItemSelected(false);
            }
            holder.setItemSelected(true);

            lastSelectedViewHolder = holder;
        }
    }

    public long getLastSelectedItemId() {
        return lastSelectedItemId;
    }

    public void setLastSelectedItemId(long lastSelectedItemId) {
        this.lastSelectedItemId = lastSelectedItemId;
    }
}
