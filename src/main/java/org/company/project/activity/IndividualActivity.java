package org.company.project.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import org.company.project.MyApplication;
import org.company.project.R;

public class IndividualActivity extends DrawerActivity {

    public static final String EXTRA_INDIVIDUAL_ID = "INDIVIDUAL_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_single_fragement);
        MyApplication.injectActivity(this);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.individual);

        setupDrawer(false, R.string.individual, false);

        long individualId = getIntent().getLongExtra(EXTRA_INDIVIDUAL_ID, -1);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_pos1, IndividualFragment.newInstance(individualId))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == null) {
            return false;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}