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
        val CATEGORY_APP = "App"
        val CATEGORY_INDIVIDUAL = "Individual"
        val CATEGORY_ABOUT = "About"
        val CATEGORY_SETTINGS = "Settings"

        // Actions
        val ACTION_APP_LAUNCH = "Launch"
        val ACTION_NEW = "New"
        val ACTION_VIEW = "View"
        val ACTION_EDIT = "Edit"
        val ACTION_DELETE = "Delete"

        // Variables
        val VARIABLE_BUILD_TYPE = "Build Type"
    }
}