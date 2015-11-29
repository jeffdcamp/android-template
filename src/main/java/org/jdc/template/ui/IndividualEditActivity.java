package org.jdc.template.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.jdc.template.R;
import org.jdc.template.dagger.Injector;
import org.jdc.template.event.IndividualSavedEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;
import pocketknife.BindExtra;
import pocketknife.PocketKnife;

public class IndividualEditActivity extends BaseActivity {

    public static final String EXTRA_ID = "INDIVIDUAL_ID";

    @Bind(R.id.ab_toolbar)
    Toolbar toolbar;

    @BindExtra(EXTRA_ID)
    long individualId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_single);
        Injector.get().inject(this);
        ButterKnife.bind(this);
        PocketKnife.bindExtras(this);

        setSupportActionBar(toolbar);
        enableActionBarBackArrow(true);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        setupActionBar();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_pos1, IndividualEditFragment.newInstance(individualId))
                    .commit();
        }
    }

    @Override
    public boolean registerEventBus() {
        return true;
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

    @Subscribe
    public void handle(IndividualSavedEvent event) {
        finish();
    }
}