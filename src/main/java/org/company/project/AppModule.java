package org.company.project;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Bus;

import org.company.project.event.AndroidBus;
import org.company.project.webservice.ServiceModule;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        ServiceModule.class
})
public class AppModule {
    private final App app;

    public AppModule(App application) {
        this.app = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides
    public SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    public NotificationManager provideNotificationManager(Application application) {
        return (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    Bus provideBus() {
        // AndroidBus is a version of the Bus that is safe to call from any thread to the main thread
        return new AndroidBus();
    }

    @Provides
    @Singleton
    public Analytics provideAnalytics() {
        // Only send analytics to Google Analytics with versions of the app that are NOT debuggable (such as BETA or RELEASE)
        if (BuildConfig.DEBUG) {
            return new Analytics() {
                @Override public void send(Map<String, String> params) {
                    Log.d(TAG, String.valueOf(params));
                }
            };

        }

        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(app);
        Tracker tracker = googleAnalytics.newTracker(BuildConfig.ANALYTICS_KEY);
        // tracker.setSessionTimeout(300); // default is 30 seconds
        return new Analytics.GoogleAnalytics(tracker);
    }
}
