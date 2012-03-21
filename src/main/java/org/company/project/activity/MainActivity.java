package org.company.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.android.actionbarcompat.RoboActionBarActivity;
import org.company.project.MyApplication;
import org.company.project.R;

//@ContentView(R.layout.main) // cannot call this because of a compatibility issue with the ActionBar (use setContentView instead)
public class MainActivity extends RoboActionBarActivity {
    public static final String TAG = MyApplication.createTag(MainActivity.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
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
