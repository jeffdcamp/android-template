package org.company.project.activity;

import android.os.Bundle;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.menu.CommonMenu;
import org.company.project.widget.robosherlock.activity.RoboSherlockFragmentActivity;
import roboguice.inject.ContentView;

import javax.inject.Inject;

@ContentView(R.layout.main)
public class MainActivity extends RoboSherlockFragmentActivity {
    public static final String TAG = MyApplication.createTag(MainActivity.class);

    @Inject
    private CommonMenu commonMenu;

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
        return commonMenu.onOptionsItemSelected(item);
    }
}
