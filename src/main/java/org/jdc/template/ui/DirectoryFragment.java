package org.jdc.template.ui;

import android.database.Cursor;
import android.os.Bundle;
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
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.jdc.template.ui.adapter.DirectoryAdapter;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import pocketbus.Bus;
import pocketbus.Subscribe;
import pocketbus.SubscriptionRegistration;
import pocketbus.ThreadMode;
import pocketknife.BindArgument;
import pocketknife.PocketKnife;
import pocketknife.SaveState;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DirectoryFragment extends BaseFragment implements SearchView.OnQueryTextListener, ActionMode.Callback {
    public static final String TAG = App.createTag(DirectoryFragment.class);

    private static final String ARGS_DUAL_PANE = "DUAL_PANE";

    @Inject
    Bus bus;
    @Inject
    Analytics analytics;
    @Inject
    IndividualManager individualManager;

    @Bind(R.id.recycler_list)
    RecyclerView recyclerView;

    @BindArgument(ARGS_DUAL_PANE)
    boolean dualPane = false;

    @SaveState
    long lastSelectedId = 0;

    private DirectoryAdapter adapter;
    private SubscriptionRegistration subscriptionRegistration;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Injector.get().inject(this);
        PocketKnife.restoreInstanceState(this, savedInstanceState);
        setHasOptionsMenu(true);
        setupRecyclerView();
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
    public void onStart() {
        super.onStart();
        loadList();
        subscriptionRegistration = bus.register(this);
    }

    @Override
    public void onStop() {
        bus.unregister(subscriptionRegistration);
        super.onStop();
    }

    public void loadList() {
        Observable<Cursor> observable = individualManager.findCursorAllRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        addSubscription(observable.subscribe(cursor -> dataLoaded(cursor)));
    }

    @Override
    public void onDestroy() {
        adapter.changeCursor(null); // close cursor
        super.onDestroy();
    }

    public void dataLoaded(Cursor data) {
        if (dualPane) {
            selectPosition(lastSelectedId);
        }

        adapter.changeCursor(data);
    }

    @Subscribe(ThreadMode.MAIN)
    public void handle(DirectoryItemClickedEvent event) {
        selectPosition(event.getItemId());
    }

    @Subscribe(ThreadMode.MAIN)
    public void handle(IndividualSavedEvent event) {
        loadList();
        bus.post(new DirectoryItemSelectedEvent(event.getId()));
    }

    @Subscribe(ThreadMode.MAIN)
    public void handle(IndividualDeletedEvent event) {
        loadList();
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
