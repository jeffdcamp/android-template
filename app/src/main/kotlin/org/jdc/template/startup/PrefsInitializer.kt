package org.jdc.template.startup

import android.content.Context
import androidx.startup.Initializer
import org.jdc.template.model.prefs.PrefsManager

class PrefsInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        PrefsManager.init(context.applicationContext)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}