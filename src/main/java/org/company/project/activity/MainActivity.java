package org.company.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.widget.robosherlock.activity.RoboSherlockFragmentActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.main)
public class MainActivity extends RoboSherlockFragmentActivity {
    public static final String TAG = MyApplication.createTag(MainActivity.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.common, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                this.onSearchRequested();
                return true;
            case R.id.menu_item_settings:
                Intent settingIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingIntent);
                return true;
            case R.id.menu_item_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            default:
                //Force Closing is not exactly a graceful way to handle this...
                Log.i(TAG, "Unknown common menu item id: " + item.getItemId() + ", ignoring");
                //throw new IllegalArgumentException("Unknown common menu item id: " + item.getItemId());
                return false;
        }
    }
}
