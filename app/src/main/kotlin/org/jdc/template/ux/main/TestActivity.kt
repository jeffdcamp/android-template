package org.jdc.template.ux.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.ui.navigation.SimpleNavActivityRoute

/**
 * Used to test navigating to an Activity
 */
@AndroidEntryPoint
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)
    }
}

object TestRoute : SimpleNavActivityRoute("test")
