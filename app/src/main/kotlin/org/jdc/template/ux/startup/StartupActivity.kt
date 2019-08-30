package org.jdc.template.ux.startup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.vikingsen.inject.viewmodel.savedstate.SavedStateViewModelFactory
import org.jdc.template.R
import org.jdc.template.inject.Injector
import org.jdc.template.ui.ThemeManager
import org.jdc.template.ui.activity.BaseActivity
import org.jdc.template.ux.main.MainActivity
import javax.inject.Inject

class StartupActivity : BaseActivity() {

    @Inject
    lateinit var themeManager: ThemeManager
    @Inject
    lateinit var viewModelFactoryFactory: SavedStateViewModelFactory.Factory

    private val viewModel by viewModels<StartupViewModel> { viewModelFactoryFactory.create(this, null) }

    private val debugStartup = false

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        setupViewModelObservers()

        themeManager.applyTheme()

        @Suppress("ConstantConditionIf") // used for debugging
        if (debugStartup) {
            devPauseStartup()
        } else {
            viewModel.startup()
        }
    }

    private fun setupViewModelObservers() {
        // Events
        viewModel.onStartupFinishedEvent.observeKt {
            showStartActivity()
        }
    }

    private fun showStartActivity() {
        val intent = Intent(application, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.nothing) // no animation
    }

    private fun devPauseStartup() {
        MaterialDialog(this).show {
            lifecycleOwner(this@StartupActivity)
            message(text = "Paused for debugger attachment")
            positiveButton(text = "OK") { viewModel.startup() }
        }
    }
}
