package org.jdc.template.ui.navigation

import android.content.Context

object WorkManagerStatusRoute : SimpleNavRoute("workManagerStatus") {
    override fun getLabel(context: Context): String? {
        return "Work Manager Status"
    }
}