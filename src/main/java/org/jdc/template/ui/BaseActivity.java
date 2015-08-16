package org.jdc.template.ui;

import android.support.v7.app.AppCompatActivity;

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
}
