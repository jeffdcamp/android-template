package org.jdc.template.ui.activity

import android.content.Intent
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
        setContentView(R.layout.activity_single_fragment_no_toolbar)

        enableActionBarBackArrow(true)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_pos1, SettingsFragment.newInstance()).commit()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // This has to be here for onActivityResult to work in the fragment
        super.onActivityResult(requestCode, resultCode, data)
    }
}