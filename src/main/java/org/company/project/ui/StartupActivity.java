package org.company.project.ui;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;

import org.company.project.Analytics;
import org.company.project.App;
import org.company.project.BuildConfig;
import org.company.project.R;
import org.company.project.task.StartupTask;

import javax.inject.Inject;

public class StartupActivity extends Activity {

    @Inject
    StartupTask startupTask;

    @Inject
    Analytics analytics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        App.getInjectComponent(this).inject(this);

        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_APP)
                .setAction(Analytics.ACTION_APP_LAUNCH)
                .setLabel(BuildConfig.BUILD_TYPE)
                .build());

        startup();
    }

    private void startup() {
        startupTask.init(this).execute("");
    }

}
