package org.jdc.template.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class BaseActivity extends AppCompatActivity {

    @Inject
    EventBus bus;

    @Override
    public void onStart() {
        super.onStart();

        if (registerEventBus()) {
            bus.register(this);
        }
    }

    @Override
    public void onStop() {
        if (registerEventBus()) {
            bus.unregister(this);
        }
        super.onStop();
    }

    public boolean registerEventBus() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (allowFinishOnHome() && item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean allowFinishOnHome() {
        return true;
    }

    public void enableActionBarBackArrow(boolean enable) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(enable);
            actionBar.setDisplayHomeAsUpEnabled(enable);
        }
    }
}
