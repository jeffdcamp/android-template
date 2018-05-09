package org.jdc.template.inject

import android.app.Application

object Injector {
    // AppComponent may need to be init() from either App, Service or Receiver and must be the same instance (leave as AppComponent?)
    private var appComponent: AppComponent? = null

    fun init(app: Application) {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder().appModule(AppModule(app)).build()
        }
    }

    fun get(): AppComponent {
        appComponent?.let { return it }
        throw IllegalStateException("appComponent is null. Call init() prior to calling get()")
    }
}
