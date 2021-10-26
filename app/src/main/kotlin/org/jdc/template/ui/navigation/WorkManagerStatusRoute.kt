package org.jdc.template.ui.navigation

import android.content.Context

@Suppress("MatchingDeclarationName")
object WorkManagerStatusRoute : SimpleNavFragmentRoute("workManagerStatus") {
    override fun getLabel(context: Context): String {
        return "Work Manager Status"
    }
}