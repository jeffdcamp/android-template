package org.company.project.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
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
    public static final String TAG = MyApplication.createTag(Activity.class);

    @Inject
    private MyApplication myApplication;

    @InjectView(R.id.version_info)
    private TextView versionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);

        versionTextView.setText(getVersionName());
    }

    private String getVersionName() {
        String versionString = "--not found--";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo("org.company.project", PackageManager.GET_META_DATA);
            versionString = pInfo.versionName + " (" + myApplication.readBuildNumber() + ")";
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "Cannon find version name");
        }
        return versionString;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MyApplication.navigateHome(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
