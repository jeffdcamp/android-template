package org.company.project.activity;

import android.os.Bundle;
import org.company.project.R;
import roboguice.activity.RoboPreferenceActivity;

/**
 * @author jcampbell
 */
public class SettingsActivity extends RoboPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}