package org.company.project.activity;

import android.os.Bundle;
import org.company.project.R;
import org.company.project.widget.activity.RoboSherlockPreferenceActivity;

/**
 * @author jcampbell
 */
public class SettingsActivity extends RoboSherlockPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}