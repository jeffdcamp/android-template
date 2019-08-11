package org.jdc.template

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber

interface Analytics {

    /**
     * @param name Name of event (Built in Firebase FirebaseAnalytics.Event.*)
     * @param params Parameters for event.  Optional. (Built in keys Firebase FirebaseAnalytics.Param.*)
     */
    fun logEvent(name: String, params: Map<String, String>? = null) {}

    class FirebaseAnalyticsWrapper(private val firebaseAnalytics: FirebaseAnalytics) : Analytics {

        override fun logEvent(name: String, params: Map<String, String>?) {
            val bundle = if (params != null) {
                val bundle = Bundle()

                params.forEach { param ->
                    bundle.putString(param.key, param.value)
                }

                bundle
            } else {
                null
            }

            firebaseAnalytics.logEvent(name, bundle)
        }
    }

    class DebugAnalytics() : Analytics {
        override fun logEvent(name: String, params: Map<String, String>?) {
            Timber.d("Analytics Name: [$name]  Params: [$params]")
        }
    }

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
}