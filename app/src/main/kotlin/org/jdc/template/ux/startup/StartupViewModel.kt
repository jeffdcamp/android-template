package org.jdc.template.ux.startup

import androidx.lifecycle.SavedStateHandle
import com.squareup.inject.assisted.Assisted
import com.vikingsen.inject.viewmodel.ViewModelInject
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.ui.viewmodel.BaseViewModel
import timber.log.Timber

class StartupViewModel
@ViewModelInject constructor(
        private val analytics: Analytics,
        @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<StartupViewModel.Event>() {
    private var currentProgressCount = 0

    fun startup() = flow<StartupProgress> {
        analytics.logEvent(Analytics.EVENT_LAUNCH_APP, mapOf(Analytics.PARAM_BUILD_TYPE to BuildConfig.BUILD_TYPE))

        // do startup work here...
        showProgress(this, "Doing stuff")

        sendEvent(Event.StartupFinishedEvent)
    }

    private suspend fun showProgress(flowCollector: FlowCollector<StartupProgress>, message: String) {
        Timber.i("Startup progress: [%s]", message)
        flowCollector.emit(StartupProgress(currentProgressCount + 1, message))
    }

    companion object {
        const val TOTAL_STARTUP_PROGRESS = 3
    }

    sealed class Event {
        object StartupFinishedEvent : Event()
    }

    data class StartupProgress(val progress: Int, val message: String = "", val indeterminate: Boolean = false)
}