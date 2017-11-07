package org.jdc.template.ux.startup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.analytics.HitBuilders
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.R
import org.jdc.template.inject.Injector
import org.jdc.template.ux.directory.DirectoryActivity
import javax.inject.Inject

class StartupActivity : Activity() {

    @Inject
    lateinit var analytics: Analytics

    private val compositeJob = Job()

    private val debugStartup = false

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        @Suppress("ConstantConditionIf") // used for debugging
        if (debugStartup) {
            devPauseStartup()
        } else {
            startUp()
        }
    }

    override fun onStop() {
        compositeJob.cancel()
        super.onStop()
    }

    private fun startUp() {
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_APP).setAction(Analytics.ACTION_APP_LAUNCH).setLabel(BuildConfig.BUILD_TYPE).build())

        compositeJob.attachChild(launch(UI) {
            run(coroutineContext + CommonPool) {
                // do some startup stuff
            }

            showStartActivity()
        })
    }

    private fun showStartActivity() {
        val intent = Intent(application, DirectoryActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.nothing) // no animation
    }

    private fun devPauseStartup() {
        MaterialDialog.Builder(this)
                .content("Paused for debugger attachment")
                .positiveText("OK")
                .onPositive { _, _ -> startUp() }
                .show()
    }
}
