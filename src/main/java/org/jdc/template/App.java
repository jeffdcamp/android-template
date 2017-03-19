package org.jdc.template;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.evernote.android.job.JobManager;

import org.jdc.template.inject.Injector;
import org.jdc.template.job.AppJobCreator;
import org.jdc.template.log.DebugTree;
import org.jdc.template.log.ReleaseTree;

import pocketbus.Registry;
import timber.log.Timber;

@Registry // PocketBus Registry
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Injector.init(this);
        JobManager.create(this).addJobCreator(new AppJobCreator());

        setupLogging();
    }

    private void setupLogging() {
        // Always register Crashltyics (even if CrashlyticsTree is not planted)
//        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }

        if (!BuildConfig.BUILD_TYPE.equals("debug")) {
            // Plant Crashlytics
            // Log.e(...) will log a non-fatal crash in Crashlytics
            // Timber.plant(new CrashlyticsTree());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (getFilesDir() != null) {
            MultiDex.install(this);
        } else {
            // During app install it might have experienced "INSTALL_FAILED_DEXOPT" (reinstall is the only known work-around)
            // https://code.google.com/p/android/issues/detail?id=8886
            String message = getString(R.string.app_name) + " is in a bad state, please uninstall/reinstall";
            Timber.e(message);
        }
    }
}
