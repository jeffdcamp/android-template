package org.jdc.template.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import org.jdc.template.R
import org.jdc.template.dagger.Injector

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.get().inject(this)
        super.onCreate(savedInstanceState) // must be done after inject(this) so that onCreatePreferences(...) can use injected variables
    }

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // avoids potentially leaked intent receiver
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        return false
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

    }

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}
