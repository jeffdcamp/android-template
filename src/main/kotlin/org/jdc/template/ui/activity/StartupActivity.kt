package org.jdc.template.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.analytics.HitBuilders
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.R
import org.jdc.template.inject.Injector
import org.jdc.template.model.database.DatabaseManager
import org.jdc.template.util.RxUtil
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class StartupActivity : Activity() {

    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var databaseManager: DatabaseManager

    private val startupActivityClass = DirectoryActivity::class.java
    private var perfTime: Long = 0

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_APP).setAction(Analytics.ACTION_APP_LAUNCH).setLabel(BuildConfig.BUILD_TYPE).build())


        RxUtil.just { startup() }
                .subscribeOn(Schedulers.io())
                //.filter(success -> success) // bail on fail?
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> postStartup() }
    }

    private fun startup(): Boolean {
        perfTime = System.currentTimeMillis()
        databaseManager.initDatabaseConnection()
        return true
    }

    private fun postStartup() {
        Timber.d("Startup Elapsed Time: ${(System.currentTimeMillis() - perfTime)}ms")

        val i = Intent(this, startupActivityClass)
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        startActivity(i)
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.nothing) // no animation
    }
}
