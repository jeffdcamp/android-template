package org.jdc.template.ux.startup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.util.ext.withLifecycleOwner
import org.jdc.template.ux.main.MainActivity
import timber.log.Timber

@AndroidEntryPoint
class StartupActivity : AppCompatActivity() {

    private val viewModel: StartupViewModel by viewModels()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        withLifecycleOwner(this) {
            // Events
            viewModel.eventChannel.receiveWhenStarted { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: StartupViewModel.Event) {
        when (event) {
            is StartupViewModel.Event.StartupProgress -> Timber.i("Startup Progress ${event.progress}")
            is StartupViewModel.Event.StartupFinished -> showStartActivity()
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
        MaterialAlertDialogBuilder(this)
            .setMessage("Paused for debugger attachment")
            .setPositiveButton("OK") { _, _ -> viewModel.debugResumeStartup() }
    }
}
