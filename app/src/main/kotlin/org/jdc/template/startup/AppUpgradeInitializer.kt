package org.jdc.template.startup

import android.content.Context
import androidx.startup.Initializer
import org.koin.androix.startup.KoinInitializer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Suppress("OPT_IN_USAGE")
class AppUpgradeInitializer : Initializer<Unit>, KoinComponent {

    private val appUpgrade: AppUpgrade by inject()

    override fun create(context: Context) {
        appUpgrade.upgradeApp()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(KoinInitializer::class.java, LoggingInitializer::class.java)
    }
}
