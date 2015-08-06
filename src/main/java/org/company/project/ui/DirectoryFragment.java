package org.company.project.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;

import org.company.project.Analytics;
import org.company.project.App;
import org.company.project.R;
import org.company.project.event.DirectoryItemSelectedEvent;
import org.company.project.event.EditIndividualEvent;
import org.company.project.event.IndividualDeletedEvent;
import org.company.project.event.IndividualSavedEvent;
import org.company.project.ui.adapter.DirectoryAdapter;
import org.company.project.ui.loader.DirectoryListLoader;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
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

    @Bind(R.id.list)
    ListView listView;

    @Bind(R.id.fab_new_item)
    FloatingActionButton newItemButton;

    @BindArgument(ARGS_DUAL_PANE)
    boolean dualPane = false;

    @SaveState
    int lastSelectedPosition = 0;

    @SaveState
    int currentPositionInList = 0;

    @SaveState
    int currentPositionInListOffset = 0;

    private CursorAdapter listAdapter;

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
        return R.layout.directory_fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInjectComponent(this).inject(this);
        PocketKnife.restoreInstanceState(this, savedInstanceState);
        setHasOptionsMenu(true);

        listView.setChoiceMode(dualPane ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);

        // setup the right edge border
        listView.setBackgroundResource(dualPane ? R.drawable.listview_dual_background : 0);

        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        View v = listView.getChildAt(0);
        currentPositionInListOffset = v == null ? 0 : v.getTop();
        currentPositionInList = listView.getFirstVisiblePosition();

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
    public void onIndividualSaved(IndividualSavedEvent event) {
        getLoaderManager().restartLoader(0, null, this);
        postListItemSelected(event.getId());
    }

    @Subscribe
    public void onIndividualDeleted(IndividualDeletedEvent event) {
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
        if (listAdapter == null) {
            listAdapter = new DirectoryAdapter(getActivity(), data);
            listView.setAdapter(listAdapter);
        } else {
            listAdapter.changeCursor(data);
        }

        if (dualPane) {
            selectPosition(lastSelectedPosition, -1, false);
        }

        listView.setSelectionFromTop(currentPositionInList, currentPositionInListOffset);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (listAdapter != null) {
            listAdapter.changeCursor(null);
        }
    }

    @OnItemClick(R.id.list)
    public void onListItemClick(int position, long id) {
        selectPosition(position, id, true);
    }

    private void selectPosition(int position, long id, boolean storeCurrentPos) {
        // Only if we're showing both fragments should the item be "highlighted"
        if (dualPane) {
            listView.setItemChecked(position, true);
            id = getCheckedItemId(id);
        }

        if (storeCurrentPos) {
            currentPositionInList = listView.getFirstVisiblePosition();
            View v = listView.getChildAt(0);
            currentPositionInListOffset = v == null ? 0 : v.getTop();
            lastSelectedPosition = position;
        }

        postListItemSelected(id);
    }

    private long getCheckedItemId(long defaultId) {
        long[] ids = listView.getCheckedItemIds();
        if (ids.length > 0) {
            return ids[0];
        }

        return defaultId;
    }

    private void postListItemSelected(long id) {
        Message msg = new Message();
        msg.what = MSG_SELECTED_ITEM;
        Bundle bundle = new Bundle();
        bundle.putLong("ID", id);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    private static final int MSG_SELECTED_ITEM = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_SELECTED_ITEM) {
                Bundle bundle = msg.getData();
                bus.post(new DirectoryItemSelectedEvent(bundle.getLong("ID")));
            }
        }
    };

    @OnClick(R.id.fab_new_item)
    public void onNewItemClick() {
        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                .setAction(Analytics.ACTION_NEW)
                .build());

        bus.post(new EditIndividualEvent());
    }
}
