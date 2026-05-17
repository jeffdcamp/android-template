package org.jdc.template.analytics

import android.app.Activity
import android.content.Intent

interface Analytics {
    fun upload()
    fun setDimensions(dimensions: List<String>)
    fun logEvent(event: AnalyticEvent)
    fun logScreen(screen: AnalyticScreen)
    fun logError(error: AnalyticError)
    fun enableInAppNotifications(allow: Boolean)
    fun onNewIntent(activity: Activity, intent: Intent)

    companion object {
        // Events
        const val EVENT_LAUNCH_APP = "launch_event"
        const val EVENT_VIEW_DIRECTORY = "view_directory"
        const val EVENT_VIEW_INDIVIDUAL = "view_individual"
        const val EVENT_EDIT_INDIVIDUAL = "edit_individual"
        const val EVENT_DELETE_INDIVIDUAL = "delete_individual"
        const val EVENT_VIEW_ABOUT = "view_about"
        const val EVENT_VIEW_SETTINGS = "view_settings"

        // Params
        const val PARAM_BUILD_TYPE = "build_type"
    }

    object Screen {
        const val DIRECTORY = "Directory"
        const val INDIVIDUAL = "Individual"
        const val INDIVIDUAL_EDIT = "Individual Edit"
        const val CHATS = "Chats"
        const val CHAT = "Chat"
        const val SETTINGS = "Settings"
        const val ABOUT = "About"
        const val TYPOGRAPHY = "Typography"
        const val ACKNOWLEDGMENTS = "Acknowledgments"
    }
}