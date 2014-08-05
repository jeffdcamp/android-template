package org.company.project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.event.DirectoryItemSelectedEvent;
import org.company.project.ui.menu.CommonMenu;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class DirectoryActivity extends DrawerActivity {

    @Inject
    CommonMenu commonMenu;

    @Inject
    Bus bus;

    private boolean dualPane = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_list);
        MyApplication.injectActivity(this);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.nav_main);

        setupDrawer(true, R.string.nav_main, false);

        dualPane = ButterKnife.findById(this, R.id.fragment_pos2) != null;

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_pos1, DirectoryFragment.newInstance(dualPane))
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return commonMenu.onOptionsItemSelected(this, item) || super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Subscribe
    public void onListItemClicked(DirectoryItemSelectedEvent event) {
        if (dualPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_pos2, IndividualFragment.newInstance(event.getId()))
                    .commit();
        } else {
            Intent intent = new Intent(this, IndividualActivity.class);
            intent.putExtra(IndividualActivity.EXTRA_ID, event.getId());
            startActivity(intent);
        }
    }
}
