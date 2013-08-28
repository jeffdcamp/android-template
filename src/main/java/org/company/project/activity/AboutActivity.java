package org.company.project.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
    public AboutActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        MyApplication.injectActivity(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView versionTextView = (TextView) findViewById(R.id.version_info);
        versionTextView.setText(getString(R.string.build_number));
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
