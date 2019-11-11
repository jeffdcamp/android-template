package org.jdc.template.ux.startup

import androidx.lifecycle.viewModelScope
import com.vikingsen.inject.viewmodel.ViewModelInject
import kotlinx.coroutines.launch
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.ui.viewmodel.BaseViewModel
import timber.log.Timber

class StartupViewModel
@ViewModelInject constructor(
        private val analytics: Analytics
) : BaseViewModel<StartupViewModel.Event>() {
    private val debugStartup = false
    private var currentProgressCount = 0

    init {
        @Suppress("ConstantConditionIf") // used for debugging
        if (!debugStartup) {
            startup()
        }
    }

    private fun startup() = viewModelScope.launch {
        analytics.logEvent(Analytics.EVENT_LAUNCH_APP, mapOf(Analytics.PARAM_BUILD_TYPE to BuildConfig.BUILD_TYPE))

        // do startup work here...
        showProgress( "Doing stuff")

        sendEvent(Event.StartupFinished)
    }

    fun debugResumeStartup() {
        startup()
    }

    private suspend fun showProgress(message: String) {
        Timber.i("Startup progress: [%s]", message)
        sendEvent(Event.StartupProgress(currentProgressCount + 1, message))
    }

    companion object {
        const val TOTAL_STARTUP_PROGRESS = 3
    }

    sealed class Event {
        class StartupProgress(val progress: Int, val message: String = "", val indeterminate: Boolean = false) : Event()
        object StartupFinished : Event()
    }
}