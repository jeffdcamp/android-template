package org.company.project.activity;

import android.os.Bundle;
import com.actionbarsherlock.view.MenuItem;
import org.company.project.R;
import org.company.project.widget.robosherlock.activity.RoboSherlockPreferenceActivity;

/**
 * @author jcampbell
 */
public class SettingsActivity extends RoboSherlockPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}