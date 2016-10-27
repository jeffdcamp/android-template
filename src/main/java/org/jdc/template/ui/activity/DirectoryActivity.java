package org.jdc.template.ui.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;

import org.jdc.template.Analytics;
import org.jdc.template.InternalIntents;
import org.jdc.template.R;
import org.jdc.template.inject.Injector;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.model.database.main.individual.IndividualConst;
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.jdc.template.ui.adapter.DirectoryAdapter;
import org.jdc.template.ui.menu.CommonMenu;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pocketknife.PocketKnife;
import pocketknife.SaveState;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DirectoryActivity extends DrawerActivity implements SearchView.OnQueryTextListener {

    @Inject
    Analytics analytics;
    @Inject
    CommonMenu commonMenu;
    @Inject
    InternalIntents internalIntents;
    @Inject
    IndividualManager individualManager;

    @BindView(R.id.ab_toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerView;

    @SaveState
    long lastSelectedId = 0;

    private DirectoryAdapter adapter;
    private long modelTs;

    public DirectoryActivity() {
        Injector.get().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        ButterKnife.bind(this);

        super.setupDrawerWithDrawerButton(toolbar, R.string.drawer_main);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new DirectoryAdapter(this);
        adapter.setListener(selectedItemId -> internalIntents.showIndividual(DirectoryActivity.this, selectedItemId));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lastSelectedId = adapter.getLastSelectedItemId();
        PocketKnife.saveInstanceState(this, outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.directory_menu, menu);
        getMenuInflater().inflate(R.menu.common_menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setQueryHint(getString(R.string.menu_search_hint));
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reloadData();
    }

    public void loadList() {
        modelTs = individualManager.getLastTableModifiedTs();
        Observable<List<Individual>> observable = individualManager.findAllBySelectionRx(null, null, IndividualConst.C_FIRST_NAME + ", " + IndividualConst.C_LAST_NAME)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        addSubscription(observable.subscribe(data -> dataLoaded(data)));
    }

    public void reloadData() {
        long lastTableModifiedTs = individualManager.getLastTableModifiedTs();
        if (modelTs < lastTableModifiedTs) {
            modelTs = lastTableModifiedTs;
            adapter.clear();
            loadList();
        }
    }

    public void dataLoaded(List<Individual> data) {
        adapter.set(data);
    }

    protected boolean allowFinishOnHome() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return commonMenu.onOptionsItemSelected(this, item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @OnClick(R.id.fab_new_item)
    public void onNewItemClick() {
        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                .setAction(Analytics.ACTION_NEW)
                .build());

        internalIntents.editIndividual(this, -1);
    }
}
