package org.jdc.template.ui;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;

import org.jdc.template.Analytics;
import org.jdc.template.BuildConfig;
import org.jdc.template.R;
import org.jdc.template.dagger.Injector;
import org.jdc.template.task.StartupTask;

import javax.inject.Inject;

public class StartupActivity extends Activity {

    @Inject
    StartupTask startupTask;

    @Inject
    Analytics analytics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Injector.get().inject(this);

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
