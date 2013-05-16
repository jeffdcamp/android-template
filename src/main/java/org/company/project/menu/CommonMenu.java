package org.company.project.menu;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.actionbarsherlock.view.MenuItem;
import com.google.inject.Singleton;
import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.activity.AboutActivity;
import org.company.project.activity.SettingsActivity;

import javax.inject.Inject;

@Singleton
public class CommonMenu {
    public static final String TAG = MyApplication.createTag(CommonMenu.class);

    @Inject
    private Context context;

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
//                this.onSearchRequested();
                return true;
            case R.id.menu_item_settings:
                Intent settingIntent = new Intent(context, SettingsActivity.class);
                context.startActivity(settingIntent);
                return true;
            case R.id.menu_item_about:
                Intent aboutIntent = new Intent(context, AboutActivity.class);
                context.startActivity(aboutIntent);
                return true;
            default:
                //Force Closing is not exactly a graceful way to handle this...
                Log.i(TAG, "Unknown common menu item id: " + item.getItemId() + ", ignoring");
                //throw new IllegalArgumentException("Unknown common menu item id: " + item.getItemId());
                return false;
        }
    }
}
