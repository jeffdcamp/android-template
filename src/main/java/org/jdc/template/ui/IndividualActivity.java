package org.jdc.template.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.jdc.template.InternalIntents;
import org.jdc.template.R;
import org.jdc.template.dagger.Injector;
import org.jdc.template.event.EditIndividualEvent;
import org.jdc.template.event.IndividualDeletedEvent;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pocketbus.Bus;
import pocketbus.Registrar;
import pocketbus.Subscribe;
import pocketbus.ThreadMode;
import pocketknife.BindExtra;
import pocketknife.PocketKnife;

public class IndividualActivity extends DrawerActivity {

    public static final String EXTRA_ID = "INDIVIDUAL_ID";

    @Bind(R.id.ab_toolbar)
    Toolbar toolbar;

    @BindExtra(EXTRA_ID)
    long individualId;

    @Inject
    InternalIntents internalIntents;
    @Inject
    Bus bus;

    private Registrar registrar = new IndividualActivityRegistrar(this);

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
        bus.register(registrar);
    }

    @Override
    protected void onStop() {
        bus.unregister(registrar);
        super.onStop();
    }

    @Subscribe(ThreadMode.MAIN)
    public void handle(EditIndividualEvent event) {
        internalIntents.editIndividual(this, event.getId());
    }

    @Subscribe(ThreadMode.MAIN)
    public void handle(IndividualDeletedEvent event) {
        finish();
    }
}