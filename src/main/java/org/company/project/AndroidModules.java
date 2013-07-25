package org.company.project;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(
        library = true // library=false will fail the build if any dependency is unused. Use this to make sure you don't have too many @Provides methods.﻿
)
public class AndroidModules {
    private final MyApplication application;

    public AndroidModules(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ForApplication
    public Context provideApplicationContext() {
        return application;
    }

    @Provides
    public SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    public NotificationManager provideNotificationManager() {
        return (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
