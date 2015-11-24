package org.jdc.template.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.jdc.template.InternalIntents;
import org.jdc.template.R;
import org.jdc.template.dagger.Injector;
import org.jdc.template.event.DirectoryItemSelectedEvent;
import org.jdc.template.event.EditIndividualEvent;
import org.jdc.template.ui.menu.CommonMenu;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class DirectoryActivity extends DrawerActivity {

    @Inject
    CommonMenu commonMenu;

    @Inject
    InternalIntents internalIntents;

    @Bind(R.id.ab_toolbar)
    Toolbar toolbar;

    private boolean dualPane = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_list);
        Injector.get().inject(this);
        ButterKnife.bind(this);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        super.setupDrawerWithDrawerButton(toolbar, R.string.drawer_main);

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

    protected boolean allowFinishOnHome() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return commonMenu.onOptionsItemSelected(this, item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean registerEventBus() {
        return true;
    }

    // ThreadMode.Async because this cannot be called from a onLoadFinished call
    @Subscribe(threadMode = ThreadMode.Async)
    public void handle(DirectoryItemSelectedEvent event) {
        if (dualPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_pos2, IndividualFragment.newInstance(event.getId()))
                    .commit();
        } else {
            internalIntents.showIndividual(this, event.getId());
        }
    }

    @Subscribe
    public void handle(EditIndividualEvent event) {
        if (dualPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_pos2, IndividualEditFragment.newInstance(event.getId()))
                    .commit();
        } else {
            internalIntents.editIndividual(this, event.getId());
        }
    }
}
