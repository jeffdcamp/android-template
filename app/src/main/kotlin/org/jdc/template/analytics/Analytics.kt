package org.jdc.template.analytics

interface Analytics {

    /**
     * @param eventId Name of event (Built in Firebase FirebaseAnalytics.Event.*)
     * @param parameterMap Attributes/Parameters for event.  Optional. (Built in keys Firebase FirebaseAnalytics.Param.*)
     */
    fun logEvent(eventId: String, parameterMap: Map<String, String>? = null) {}

    /**
     * @param screenTitle Name of Screen
     * @param parameterMap Attributes/Parameters for screen event.  Optional. (Built in keys Firebase FirebaseAnalytics.Param.*)
     */
    fun logScreen(screenTitle: String, parameterMap: Map<String, String>? = null) {}

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