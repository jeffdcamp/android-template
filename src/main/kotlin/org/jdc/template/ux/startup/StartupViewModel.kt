package org.jdc.template.ux.startup

import android.arch.lifecycle.ViewModel
import com.google.android.gms.analytics.HitBuilders
import kotlinx.coroutines.experimental.launch
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.util.CoroutineContextProvider
import javax.inject.Inject

class StartupViewModel
@Inject constructor(
        private val cc: CoroutineContextProvider,
        private val analytics: Analytics
) : ViewModel() {

    val onStartupFinishedEvent = SingleLiveEvent<Void>()

    fun startup() {
        launch(cc.commonPool) {
            analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_APP).setAction(Analytics.ACTION_APP_LAUNCH).setLabel(BuildConfig.BUILD_TYPE).build())

            // do startup work here...

            onStartupFinishedEvent.postCall()
        }
    }
}