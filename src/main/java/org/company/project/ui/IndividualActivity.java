package org.company.project.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.company.project.App;
import org.company.project.InternalIntents;
import org.company.project.R;
import org.company.project.event.EditIndividualEvent;
import org.company.project.event.IndividualDeletedEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pocketknife.InjectExtra;
import pocketknife.PocketKnife;

public class IndividualActivity extends DrawerActivity {

    public static final String EXTRA_ID = "INDIVIDUAL_ID";

    @InjectView(R.id.ab_toolbar)
    Toolbar toolbar;

    @Inject
    InternalIntents internalIntents;

    @Inject
    Bus bus;

    @InjectExtra(EXTRA_ID)
    long individualId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragement);
        App.inject(this);
        ButterKnife.inject(this);
        PocketKnife.injectExtras(this);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        setupActionBar();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_pos1, IndividualFragment.newInstance(individualId))
                    .commit();
        }
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        setupDrawerWithBackButton(actionBar);
        actionBar.setTitle(R.string.individual);
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
    public void onEditItemClicked(EditIndividualEvent event) {
        internalIntents.editIndividual(this, event.getId());
    }

    @Subscribe
    public void onIndividualDeleted(IndividualDeletedEvent event) {
        finish();
    }
}