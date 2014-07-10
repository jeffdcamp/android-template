package org.company.project.menu;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.activity.AboutActivity;
import org.company.project.activity.SettingsActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommonMenu {
    public static final String TAG = MyApplication.createTag(CommonMenu.class);

    @Inject
    public CommonMenu() {
    }

    public boolean onOptionsItemSelected(Context context, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
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
                Log.i(TAG, "Unknown common menu item id: " + item.getItemId() + ", ignoring");
                return false;
        }
    }
}
