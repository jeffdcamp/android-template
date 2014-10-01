package org.company.project;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.squareup.otto.Bus;

import org.company.project.event.AndroidBus;
import org.company.project.ui.UiModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
                UiModule.class
        },
        injects = {
                App.class
        },
        library = true,
        complete = false
)
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
}
