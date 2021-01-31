package org.jdc.template.ux.startup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import org.jdc.template.BuildConfig
import org.jdc.template.analytics.Analytics
import org.jdc.template.util.coroutine.channel.ViewModelChannel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StartupViewModel
@Inject constructor(
    private val analytics: Analytics
) : ViewModel() {
    private val _eventChannel: ViewModelChannel<Event> = ViewModelChannel(this)
    val eventChannel: ReceiveChannel<Event> = _eventChannel

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
        showProgress("Doing stuff")

        _eventChannel.sendAsync(Event.StartupFinished)
    }

    fun debugResumeStartup() {
        startup()
    }

    private fun showProgress(message: String) {
        Timber.i("Startup progress: [%s]", message)
        _eventChannel.sendAsync(Event.StartupProgress(currentProgressCount + 1, message))
    }

    companion object {
        const val TOTAL_STARTUP_PROGRESS = 3
    }

    sealed class Event {
        class StartupProgress(val progress: Int, val message: String = "", val indeterminate: Boolean = false) : Event()
        object StartupFinished : Event()
    }
}