package org.jdc.template.ux.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.databinding.MainActivityBinding
import org.jdc.template.ui.ThemeManager
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
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appbar.mainToolbar)

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