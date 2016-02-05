package org.jdc.template.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.jdc.template.InternalIntents;
import org.jdc.template.R;
import org.jdc.template.dagger.Injector;
import org.jdc.template.event.EditIndividualEvent;
import org.jdc.template.event.IndividualDeletedEvent;
import org.jdc.template.event.RxBus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pocketknife.BindExtra;
import pocketknife.PocketKnife;

public class IndividualActivity extends DrawerActivity {

    public static final String EXTRA_ID = "INDIVIDUAL_ID";

    @Bind(R.id.ab_toolbar)
    Toolbar toolbar;

    @Inject
    InternalIntents internalIntents;

    @BindExtra(EXTRA_ID)
    long individualId;

    @Inject
    RxBus bus;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_drawer_single);
        Injector.get().inject(this);
        ButterKnife.bind(this);
        PocketKnife.bindExtras(this);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        setupDrawerWithBackButton(toolbar, R.string.individual);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_pos1, IndividualFragment.newInstance(individualId))
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        addSubscription(bus.subscribeMainThread(event -> handleSubscribeMainThread(event)));
    }

    private void handleSubscribeMainThread(Object event) {
        if (event instanceof EditIndividualEvent) {
            internalIntents.editIndividual(this, ((EditIndividualEvent) event).getId());
        } else if (event instanceof IndividualDeletedEvent) {
            finish();
        }
    }
}