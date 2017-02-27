package org.jdc.template.ui.activity;

import android.os.Bundle;

import org.jdc.template.R;
import org.jdc.template.inject.Injector;
import org.jdc.template.ui.fragment.SettingsFragment;


public class SettingsActivity extends BaseActivity {

    public SettingsActivity() {
        Injector.get().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        enableActionBarBackArrow();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_pos1, SettingsFragment.newInstance()).commit();
        }
    }
}