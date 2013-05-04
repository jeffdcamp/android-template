package org.company.project.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.actionbarsherlock.view.MenuItem;
import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.widget.robosherlock.activity.RoboSherlockFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import javax.inject.Inject;

/**
 *
 * @author jcampbell
 */
@ContentView(R.layout.about)
public class AboutActivity extends RoboSherlockFragmentActivity {
    public static final String TAG = MyApplication.createTag(AboutActivity.class);

    @Inject
    private MyApplication myApplication;

    @InjectView(R.id.version_info)
    private TextView versionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        versionTextView.setText(getVersionName());
    }

    private String getVersionName() {
        String versionString = "--not found--";
        try {
            versionString = myApplication.getVersionText(this);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "Cannon find version name");
        }
        return versionString;
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
