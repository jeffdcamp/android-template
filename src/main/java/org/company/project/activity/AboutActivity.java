package org.company.project.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.company.project.MyApplication;
import org.company.project.R;

import javax.inject.Inject;

/**
 *
 * @author jcampbell
 */
public class AboutActivity extends ActionBarActivity {
    public static final String TAG = MyApplication.createTag(AboutActivity.class);

    @Inject
    public MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        MyApplication.injectActivity(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView versionTextView = (TextView) findViewById(R.id.version_info);
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
