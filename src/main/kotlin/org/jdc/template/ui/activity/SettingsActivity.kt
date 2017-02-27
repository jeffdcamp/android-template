package org.jdc.template.ui.activity

import android.os.Bundle
import org.jdc.template.R
import org.jdc.template.inject.Injector
import org.jdc.template.ui.fragment.SettingsFragment


class SettingsActivity : BaseActivity() {
    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        enableActionBarBackArrow(true)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_pos1, SettingsFragment.newInstance()).commit()
        }
    }
}