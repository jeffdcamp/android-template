package org.jdc.template.dagger;

import android.app.Application;

public class Injector {
    private static AppComponent appComponent;

    private Injector() {
        //Purposefully left blank
    }

    public static void init(Application app) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(app))
                .build();
    }

    /**
     * Retrieves the AppComponent responsible for injection.
     *
     * @return The AppComponent responsible for injection
     */
    public static AppComponent get() {
        return appComponent;
    }
}
