package org.company.project.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import org.company.project.R;

/**
 * @author jcampbell
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}