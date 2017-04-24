package org.jdc.template.ux.startup

import android.app.Application
import com.google.android.gms.analytics.HitBuilders
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.model.database.DatabaseManager
import org.jdc.template.ui.mvp.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class StartupPresenter @Inject constructor(val application: Application,
                                           val analytics: Analytics,
                                           val databaseManager: DatabaseManager): BasePresenter() {

    private lateinit var view: StartupContract.View
    private var perfTime: Long = 0

    fun init(view: StartupContract.View) {
        this.view = view
    }

    override fun load(): Job? {
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_APP).setAction(Analytics.ACTION_APP_LAUNCH).setLabel(BuildConfig.BUILD_TYPE).build())

        return launch(UI) {
            run(context + CommonPool) {
              startup()
            }

            postStartup()
        }
    }

    private fun startup(): Boolean {
        perfTime = System.currentTimeMillis()
        databaseManager.initDatabaseConnection()
        return true
    }

    private fun postStartup() {
        Timber.d("Startup Elapsed Time: ${(System.currentTimeMillis() - perfTime)}ms")

        view.showStartActivity()
        view.close()
    }
}