package org.jdc.template.startup

import android.content.Context
import androidx.startup.Initializer
import org.jdc.template.model.config.RemoteConfig
import org.koin.androix.startup.KoinInitializer
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RemoteConfigInitializer : Initializer<Unit>, KoinComponent {

    private val remoteConfig: RemoteConfig by inject()

    override fun create(context: Context) {
        remoteConfig.fetchAsync()
        remoteConfig.activateAsync()
    }

    @OptIn(KoinExperimentalAPI::class)
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(KoinInitializer::class.java, LoggingInitializer::class.java)
    }
}
