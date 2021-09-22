package org.jdc.template.ux.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.databinding.TestActivityBinding
import org.jdc.template.ui.navigation.SimpleNavActivityRoute

/**
 * Used to test navigating to an Activity
 */
@AndroidEntryPoint
class TestActivity : AppCompatActivity() {
    private lateinit var binding: TestActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TestActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

object TestRoute : SimpleNavActivityRoute("test")
