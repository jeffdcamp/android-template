package org.jdc.template

import com.google.android.gms.analytics.Tracker

interface Analytics {

    fun send(params: Map<String, String>)

    class GoogleAnalytics(private val tracker: Tracker) : Analytics {

        override fun send(params: Map<String, String>) {
            tracker.send(params)
        }
    }

    companion object {
        // Categories
        const val CATEGORY_APP = "App"
        const val CATEGORY_DIRECTORY = "Individual"
        const val CATEGORY_INDIVIDUAL = "Individual"
        const val CATEGORY_ABOUT = "About"
        const val CATEGORY_SETTINGS = "Settings"

        // Actions
        const val ACTION_APP_LAUNCH = "Launch"
        const val ACTION_NEW = "New"
        const val ACTION_VIEW = "View"
        const val ACTION_EDIT = "Edit"
        const val ACTION_EDIT_SAVE = "Edit Save"
        const val ACTION_DELETE = "Delete"

        // Variables
        const val VARIABLE_BUILD_TYPE = "Build Type"
    }
}