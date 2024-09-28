package org.jdc.template.startup

import android.content.Context
import androidx.startup.Initializer
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.jdc.template.model.config.RemoteConfig

class RemoteConfigInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val applicationContext = checkNotNull(context.applicationContext) { "Missing Application Context" }
        val injector = EntryPoints.get(applicationContext, RemoteConfigInitializerInjector::class.java)

        val remoteConfig = injector.getRemoteConfig()

        remoteConfig.fetchAsync()
        remoteConfig.activateAsync()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(LoggingInitializer::class.java)
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface RemoteConfigInitializerInjector {
        fun getRemoteConfig(): RemoteConfig
    }
}