package org.jdc.template.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;

import org.jdc.template.Analytics;
import org.jdc.template.App;
import org.jdc.template.BuildConfig;
import org.jdc.template.R;
import org.jdc.template.dagger.Injector;
import org.jdc.template.domain.DatabaseManager;
import org.jdc.template.util.RxUtil;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StartupActivity extends Activity {
    public static final String TAG = App.createTag(StartupActivity.class);

    @Inject
    Analytics analytics;
    @Inject
    DatabaseManager databaseManager;

    private Class startupActivityClass = DirectoryActivity.class;
    private long perfTime = 0;

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

        RxUtil.just(() -> startup())
                .subscribeOn(Schedulers.io())
//                .filter(success -> success) // bail on fail?
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> postStartup(success));
    }

    private boolean startup() {
        perfTime = System.currentTimeMillis();
        databaseManager.initDatabaseConnection();
        return true;
    }

    private void postStartup(boolean success) {
        Log.d(TAG, "Startup Elapsed Time:" + (System.currentTimeMillis() - perfTime) + "ms");

        Intent i = new Intent(this, startupActivityClass);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(i);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.nothing); // no animation
    }
}
