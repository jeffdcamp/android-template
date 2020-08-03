package org.jdc.template.startup

import android.content.Context
import androidx.startup.Initializer
import org.jdc.template.ui.notifications.NotificationChannels

class NotificationInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        NotificationChannels.registerAllChannels(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}