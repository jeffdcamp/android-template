package org.jdc.template.ux.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import org.jdc.template.R
import org.jdc.template.databinding.MainActivityBinding
import org.jdc.template.ui.activity.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding
    private val navController by lazy { findNavController(R.id.main_nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        setSupportActionBar(binding.appbar.mainToolbar)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.common_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }
}