package org.jdc.template.analytics

import timber.log.Timber

class DebugAnalytics : Analytics {
    override fun logEvent(eventId: String, parameterMap: Map<String, String>?) {
        Timber.d("Analytics log event -- EventId: [$eventId]  Params: [$parameterMap]")
    }

    override fun logScreen(screenTitle: String, parameterMap: Map<String, String>?) {
        Timber.d("Analytics log screen -- Screen Title: [$screenTitle]  Params: [$parameterMap]")
    }
}