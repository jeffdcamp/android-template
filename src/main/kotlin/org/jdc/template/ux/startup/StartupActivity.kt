package org.jdc.template.ux.startup

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import org.jdc.template.R
import org.jdc.template.inject.Injector
import org.jdc.template.ui.activity.LiveDataObserverActivity
import org.jdc.template.ux.directory.DirectoryActivity
import javax.inject.Inject

class StartupActivity : LiveDataObserverActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: StartupViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(StartupViewModel::class.java) }

    private val debugStartup = false

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupViewModelObservers()

        @Suppress("ConstantConditionIf") // used for debugging
        if (debugStartup) {
            devPauseStartup()
        } else {
            viewModel.startup()
        }
    }

    private fun setupViewModelObservers() {
        // Events
        viewModel.onStartupFinishedEvent.observe {
            showStartActivity()
        }
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
                .onPositive { _, _ -> viewModel.startup() }
                .show()
    }
}
