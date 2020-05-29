package org.jdc.template.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.vikingsen.inject.fragment.FragmentInject
import org.jdc.template.R
import org.jdc.template.prefs.Prefs
import org.jdc.template.ui.ThemeManager

class SettingsFragment
@FragmentInject constructor(
    private val prefs: Prefs,
    private val themeManager: ThemeManager
) : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onResume() {
        super.onResume()

        prefs.preferenceManager.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        prefs.preferenceManager.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        return false
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            "displayThemeType" -> {
                themeManager.applyTheme()
            }
        }
    }
}
