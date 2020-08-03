package org.jdc.template.startup

import android.content.Context
import androidx.startup.Initializer
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.jdc.template.AppUpgrade

class AppUpgradeInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val applicationContext = checkNotNull(context.applicationContext) { "Missing Application Context" }
        val injector = EntryPoints.get(applicationContext, AppUpgradeInitializerInjector::class.java)

        injector.appUpgrade.upgradeApp()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(TimberInitializer::class.java, PrefsInitializer::class.java)
    }

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface AppUpgradeInitializerInjector {
        val appUpgrade: AppUpgrade
    }
}