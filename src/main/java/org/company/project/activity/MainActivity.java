package org.company.project.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.menu.CommonMenu;

import javax.inject.Inject;

public class MainActivity extends DrawerActivity implements ActionMode.Callback, SearchView.OnQueryTextListener {
    public static final String TAG = MyApplication.createTag(MainActivity.class);

    @Inject
    public CommonMenu commonMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MyApplication.injectActivity(this);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        TextView helloTextView = (TextView) findViewById(R.id.hello_world);
        helloTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startSupportActionMode(MainActivity.this);
                return true;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.nav_main);

        setupDrawer(true, R.string.nav_main, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setQueryHint(getString(R.string.menu_search_hint));
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return commonMenu.onOptionsItemSelected(this, item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.common, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

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
}
