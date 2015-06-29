package org.company.project.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.company.project.App;
import org.company.project.InternalIntents;
import org.company.project.R;
import org.company.project.event.EditIndividualEvent;
import org.company.project.event.IndividualDeletedEvent;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pocketknife.InjectExtra;
import pocketknife.PocketKnife;

public class IndividualActivity extends DrawerActivity {

    public static final String EXTRA_ID = "INDIVIDUAL_ID";

    @Bind(R.id.ab_toolbar)
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
        setContentView(R.layout.drawer_single_fragment);
        App.getInjectComponent(this).inject(this);
        ButterKnife.bind(this);
        PocketKnife.injectExtras(this);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        setupDrawerWithBackButton(toolbar, R.string.individual);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_pos1, IndividualFragment.newInstance(individualId))
                    .commit();
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