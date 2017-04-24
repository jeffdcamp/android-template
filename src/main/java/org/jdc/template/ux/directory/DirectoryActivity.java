package org.jdc.template.ux.directory;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.jdc.template.InternalIntents;
import org.jdc.template.R;
import org.jdc.template.inject.Injector;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.ui.activity.DrawerActivity;
import org.jdc.template.ui.menu.CommonMenu;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DirectoryActivity extends DrawerActivity implements SearchView.OnQueryTextListener, DirectoryContract.View {

    @Inject
    CommonMenu commonMenu;
    @Inject
    InternalIntents internalIntents;
    @Inject
    DirectoryPresenter presenter;

    @BindView(R.id.mainToolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private DirectoryAdapter adapter;

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

        presenter.init(this);
    }

    private void setupRecyclerView() {
        adapter = new DirectoryAdapter(this);
        adapter.setListener(selectedItemId -> internalIntents.showIndividual(DirectoryActivity.this, selectedItemId));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
    protected void onResume() {
        super.onResume();
        presenter.reload(false);
    }

    @Override
    public void showIndividualList(List<Individual> data) {
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

    @Override
    public void showNewIndividual() {
        internalIntents.newIndividual(this);
    }

    @Override
    public void showIndividual(long individualId) {
        internalIntents.showIndividual(this, individualId);
    }

    @OnClick(R.id.newFloatingActionButton)
    public void onNewItemClick() {
        presenter.newItemClicked();
    }
}
