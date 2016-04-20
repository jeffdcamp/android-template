package org.jdc.template;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;
import com.evernote.android.job.JobManager;

import org.jdc.template.inject.Injector;
import org.jdc.template.job.AppJobCreator;
import org.jdc.template.model.webservice.DateTimeTypeConverter;
import org.threeten.bp.LocalDateTime;

import pocketbus.Registry;

@Registry // PocketBus Registry
public class App extends Application {
    public static final String TAG = App.createTag(App.class);

    // TODO change this for your app (pick a name similar to package name... get both raw log AND tag logs)
    public static final String DEFAULT_TAG_PREFIX = "company.";
    public static final int MAX_TAG_LENGTH = 23; // if over: IllegalArgumentException: Log tag "xxx" exceeds limit of 23 characters

    @Override
    public void onCreate() {
        super.onCreate();
        Injector.init(this);
        JobManager.create(this).addJobCreator(new AppJobCreator());

        // register json global converters
        registerJsonConverters();
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
            Log.e(TAG, message);
        }
    }

    public static String createTag(String name) {
        String fullName = DEFAULT_TAG_PREFIX + name;
        return fullName.length() > MAX_TAG_LENGTH ? fullName.substring(0, MAX_TAG_LENGTH) : fullName;
    }

    public static String createTag(Class clazz) {
        return createTag(clazz.getSimpleName());
    }

    private void registerJsonConverters() {
        LoganSquare.registerTypeConverter(LocalDateTime.class, new DateTimeTypeConverter());
    }
}
