package org.company.project.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.company.project.App;
import org.company.project.R;
import org.company.project.event.IndividualSavedEvent;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import pocketknife.BindExtra;
import pocketknife.PocketKnife;

public class IndividualEditActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "INDIVIDUAL_ID";

    @Bind(R.id.ab_toolbar)
    Toolbar toolbar;

    @Inject
    EventBus bus;

    @BindExtra(EXTRA_ID)
    long individualId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment);
        App.getInjectComponent(this).inject(this);
        ButterKnife.bind(this);
        PocketKnife.bindExtras(this);
        setSupportActionBar(toolbar);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        setupActionBar();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_pos1, IndividualEditFragment.newInstance(individualId))
                    .commit();
        }
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.individual);
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

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe
    public void onIndividualSaved(IndividualSavedEvent event) {
        finish();
    }
}