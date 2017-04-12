package org.jdc.template.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;

import org.jdc.template.Analytics;
import org.jdc.template.BuildConfig;
import org.jdc.template.R;
import org.jdc.template.inject.Injector;
import org.jdc.template.model.database.DatabaseManager;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class StartupActivity extends Activity {
    @Inject
    Analytics analytics;
    @Inject
    DatabaseManager databaseManager;

    private final Class startupActivityClass = DirectoryActivity.class;
    private long perfTime = 0;

    public StartupActivity() {
        Injector.get().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_APP)
                .setAction(Analytics.ACTION_APP_LAUNCH)
                .setLabel(BuildConfig.BUILD_TYPE)
                .build());


        Single.defer(() -> Single.just(startup()))
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
        Timber.d("Startup Elapsed Time: %d ms", (System.currentTimeMillis() - perfTime));

        Intent i = new Intent(this, startupActivityClass);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(i);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.nothing); // no animation
    }
}
