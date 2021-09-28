package org.jdc.template.ux.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.databinding.MainActivityBinding
import org.jdc.template.ui.ThemeManager
import org.jdc.template.ui.navigation.MainNav
import org.jdc.template.ui.navigation.NavUriLogger
import org.jdc.template.util.ext.withLifecycleOwner
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var themeManager: ThemeManager

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: MainActivityBinding

    private val navController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        navHostFragment.navController.apply {
            graph = MainNav.createNavGraph(this@MainActivity, navHostFragment)
            addOnDestinationChangedListener(NavUriLogger()) // debug logging
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startup(savedInstanceState)
    }

    private fun startup(savedInstanceState: Bundle?) {
        viewModel.startup()

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (viewModel.isReady) {
                        // The content is ready... start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)

                        // finish regular onCreate() code
                        finishCreate(savedInstanceState)
                        true
                    } else {
                        // The content is not ready... suspend.
                        false
                    }
                }
            }
        )
    }

    private fun finishCreate(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.mainToolbar)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        setupActionBarWithNavController(navController)

        withLifecycleOwner(this) {
            viewModel.themeFlow.collectWhenStarted { theme ->
                themeManager.applyTheme(theme)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        if (!navController.handleDeepLink(intent)) {
            super.onNewIntent(intent)
        }
    }
}