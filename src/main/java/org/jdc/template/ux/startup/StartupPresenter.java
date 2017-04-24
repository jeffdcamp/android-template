package org.jdc.template.ux.startup;

import com.google.android.gms.analytics.HitBuilders;

import org.jdc.template.Analytics;
import org.jdc.template.BuildConfig;
import org.jdc.template.model.database.DatabaseManager;
import org.jdc.template.ui.mvp.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class StartupPresenter extends BasePresenter {
    private final Analytics analytics;
    private final DatabaseManager databaseManager;

    private StartupContract.View view;
    private long perfTime = 0;

    @Inject
    public StartupPresenter(Analytics analytics, DatabaseManager databaseManager) {
        this.analytics = analytics;
        this.databaseManager = databaseManager;
    }

    public void init(StartupContract.View view) {
        this.view = view;
    }

    @Override
    public void load() {
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
        view.showStartActivity();
        view.close();
    }

}
