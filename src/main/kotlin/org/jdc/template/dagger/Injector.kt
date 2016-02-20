package org.jdc.template.dagger

import android.app.Application

object Injector {
    lateinit var appComponent: AppComponent

    fun init(app: Application) {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(app)).build()
    }

    fun get(): AppComponent {
        return appComponent
    }
}
