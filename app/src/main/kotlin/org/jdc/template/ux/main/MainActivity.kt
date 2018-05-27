package org.jdc.template.ux.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import org.jdc.template.R
import org.jdc.template.databinding.MainActivityBinding
import org.jdc.template.ui.activity.LiveDataObserverActivity

class MainActivity : LiveDataObserverActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        setSupportActionBar(binding.appbar.mainToolbar)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.main_nav_host_fragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.common_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }
}