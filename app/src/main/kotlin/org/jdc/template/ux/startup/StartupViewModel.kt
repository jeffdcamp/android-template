package org.jdc.template.ux.startup

import androidx.lifecycle.MutableLiveData
import com.vikingsen.inject.viewmodel.ViewModelInject
import kotlinx.coroutines.launch
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.livedata.EmptySingleLiveEvent
import org.jdc.template.ui.viewmodel.BaseViewModel
import timber.log.Timber

class StartupViewModel
@ViewModelInject constructor(
    private val analytics: Analytics
) : BaseViewModel() {

    val startupProgress = MutableLiveData<StartupProgress>()
    val onStartupFinishedEvent = EmptySingleLiveEvent()

    fun startup() = launch {
        analytics.logEvent(Analytics.EVENT_LAUNCH_APP, mapOf(Analytics.PARAM_BUILD_TYPE to BuildConfig.BUILD_TYPE))

        // do startup work here...
        showProgress("Doing stuff")

        onStartupFinishedEvent.postCall()
    }

    private fun showProgress(message: String) {
        Timber.i("Startup progress: [%s]", message)
        val currentProgress = startupProgress.value
        if (currentProgress == null) {
            startupProgress.postValue(StartupProgress(0, message))
        } else {
            startupProgress.postValue(StartupProgress(currentProgress.progress + 1, message))
        }
    }

    companion object {
        const val TOTAL_STARTUP_PROGRESS = 3
    }

    data class StartupProgress(val progress: Int, val message: String = "", val indeterminate: Boolean = false)
}