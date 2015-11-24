package org.jdc.template.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;

import org.jdc.template.Analytics;
import org.jdc.template.App;
import org.jdc.template.R;
import org.jdc.template.dagger.Injector;
import org.jdc.template.event.DirectoryItemClickedEvent;
import org.jdc.template.event.DirectoryItemSelectedEvent;
import org.jdc.template.event.EditIndividualEvent;
import org.jdc.template.event.IndividualDeletedEvent;
import org.jdc.template.event.IndividualSavedEvent;
import org.jdc.template.ui.adapter.DirectoryAdapter;
import org.jdc.template.ui.loader.DirectoryListLoader;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import pocketknife.BindArgument;
import pocketknife.PocketKnife;
import pocketknife.SaveState;

public class DirectoryFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, SearchView.OnQueryTextListener, ActionMode.Callback {
    public static final String TAG = App.createTag(DirectoryFragment.class);

    private static final String ARGS_DUAL_PANE = "DUAL_PANE";

    @Inject
    EventBus bus;

    @Inject
    Analytics analytics;

    @Bind(R.id.recycler_list)
    RecyclerView recyclerView;

    @BindArgument(ARGS_DUAL_PANE)
    boolean dualPane = false;

    @SaveState
    long lastSelectedId = 0;

    private DirectoryAdapter adapter;

    public static DirectoryFragment newInstance(boolean dualPane) {
        DirectoryFragment fragment = new DirectoryFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARGS_DUAL_PANE, dualPane);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PocketKnife.bindArguments(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_directory;
    }

    @Override
    public boolean registerEventBus() {
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Injector.get().inject(this);
        PocketKnife.restoreInstanceState(this, savedInstanceState);
        setHasOptionsMenu(true);
        setupRecyclerView();

        getLoaderManager().restartLoader(0, null, this);
    }

    private void setupRecyclerView() {
        adapter = new DirectoryAdapter(getActivity(), null, dualPane);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        // setup the right edge border
        recyclerView.setBackgroundResource(dualPane ? R.drawable.listview_dual_background : 0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        lastSelectedId = adapter.getLastSelectedItemId();

        PocketKnife.saveInstanceState(this, outState);
    }

    @Override
    public void onCreateOptionsMenu(@Nonnull Menu menu, @Nonnull MenuInflater inflater) {
        inflater.inflate(R.menu.directory_menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setQueryHint(getString(R.string.menu_search_hint));
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    @Subscribe
    public void handle(DirectoryItemClickedEvent event) {
        selectPosition(event.getItemId());
    }

    @Subscribe
    public void handle(IndividualSavedEvent event) {
        getLoaderManager().restartLoader(0, null, this);
        bus.post(new DirectoryItemSelectedEvent(event.getId()));
    }

    @Subscribe
    public void handle(IndividualDeletedEvent event) {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.i(TAG, "Search Submit: " + s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.i(TAG, "Search Change: " + s);
        return true;
    }

    @Override
    public boolean onCreateActionMode(@Nonnull ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.directory_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        // destroy action mode
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new DirectoryListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (dualPane) {
            selectPosition(lastSelectedId);
        }

        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (adapter != null) {
            adapter.changeCursor(null);
        }
    }

    private void selectPosition(long id) {
        // Only if we're showing both fragments should the item be "highlighted"
        if (dualPane) {
            adapter.setLastSelectedItemId(id);
        }

        bus.post(new DirectoryItemSelectedEvent(id));
    }

    @OnClick(R.id.fab_new_item)
    public void onNewItemClick() {
        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                .setAction(Analytics.ACTION_NEW)
                .build());

        bus.post(new EditIndividualEvent());
    }
}
