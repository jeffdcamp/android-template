package org.jdc.template.startup

import android.content.Context
import androidx.startup.Initializer
import co.touchlab.kermit.Logger
import org.jdc.template.BuildConfig
import org.jdc.template.util.log.SimpleCrashlyticsLogWriter

class LoggingInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Logger.setTag(BuildConfig.APPLICATION_ID)

        if (!BuildConfig.DEBUG) {
            Logger.addLogWriter(SimpleCrashlyticsLogWriter())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
