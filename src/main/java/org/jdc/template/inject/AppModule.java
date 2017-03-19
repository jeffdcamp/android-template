package org.jdc.template.inject;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import org.dbtools.android.domain.config.DatabaseConfig;
import org.jdc.template.Analytics;
import org.jdc.template.BuildConfig;
import org.jdc.template.BusRegistry;
import org.jdc.template.model.database.AppDatabaseConfig;
import org.jdc.template.json.DateTimeStringDeserializer;
import org.jdc.template.json.DateTimeStringSerializer;
import org.jdc.template.model.webservice.ServiceModule;
import org.threeten.bp.LocalDateTime;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pocketbus.Bus;
import retrofit2.converter.jackson.JacksonConverterFactory;
import timber.log.Timber;

@Module(includes = {
        ServiceModule.class
})
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
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
    public Analytics provideAnalytics() {
        // Only send analytics to Google Analytics with versions of the app that are NOT debuggable (such as BETA or RELEASE)
        if (BuildConfig.DEBUG) {
            return params -> Timber.d(String.valueOf(params));
        }

        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(application);
        Tracker tracker = googleAnalytics.newTracker(BuildConfig.ANALYTICS_KEY);
        // tracker.setSessionTimeout(300); // default is 30 seconds
        return new Analytics.GoogleAnalytics(tracker);
    }

    @Provides
    @Singleton
    DatabaseConfig provideDatabaseConfig(Application application) {
        return new AppDatabaseConfig(application);
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        Bus bus = new Bus.Builder()
                .build();
        bus.setRegistry(new BusRegistry());
        return bus;
    }

    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper() {
        SimpleModule module = new SimpleModule("Jackson MODULE");
        module.addSerializer(LocalDateTime.class, new DateTimeStringSerializer())
                .addDeserializer(LocalDateTime.class, new DateTimeStringDeserializer());

        return new ObjectMapper().registerModule(module);
    }

    @Provides
    @Singleton
    public JacksonConverterFactory provideJacksonConverterFactory(ObjectMapper objectMapper) {
        return JacksonConverterFactory.create(objectMapper);
    }

}
