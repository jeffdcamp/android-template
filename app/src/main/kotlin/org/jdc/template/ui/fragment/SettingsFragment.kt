package org.jdc.template.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.model.prefs.DisplayThemeType
import org.jdc.template.util.enumValueOfOrNull

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        setupPreferenceListeners()
    }

    private fun setupPreferenceListeners() {
        findPreference<Preference>("displayThemeType")?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
            when (newValue) {
                is String -> {
                    enumValueOfOrNull<DisplayThemeType>(newValue)?.let { theme ->
                        viewModel.setTheme(theme)
                    }
                }
            }
            true
        }

        findPreference<Preference>("workManagerStatusButton")?.setOnPreferenceClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionToWorkManagerFragment())
            true
        }
    }
}
