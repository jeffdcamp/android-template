package org.jdc.template.startup

import android.content.Context
import androidx.startup.Initializer
import co.touchlab.kermit.ExperimentalKermitApi
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.crashlytics.CrashlyticsLogWriter
import org.jdc.template.BuildConfig

class LoggingInitializer : Initializer<Unit> {

    @OptIn(ExperimentalKermitApi::class)
    override fun create(context: Context) {
        Logger.setTag(BuildConfig.APPLICATION_ID)

        if (!BuildConfig.DEBUG) {
            Logger.setMinSeverity(Severity.Info)
            Logger.addLogWriter(CrashlyticsLogWriter())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
