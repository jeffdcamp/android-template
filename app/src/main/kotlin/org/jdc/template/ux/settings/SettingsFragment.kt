package org.jdc.template.ux.settings

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.util.ext.enumValueOfOrNull
import org.jdc.template.util.ext.withLifecycleOwner

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        setupPreferenceListeners()
    }

    private fun setupPreferenceListeners() {
        findPreference<Preference>("displayThemeType")?.apply {
            // show/update setting summary
            summaryProvider = null // remove existing summary provider
            withLifecycleOwner(this@SettingsFragment) {
                viewModel.currentThemeTitleFlow.collectLatestWhenStarted { summary -> setSummary(summary) }
            }

            // handle setting change
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                when (newValue) {
                    is String -> {
                        enumValueOfOrNull<DisplayThemeType>(newValue)?.let { theme ->
                            viewModel.setTheme(theme)
                        }
                    }
                }
                true
            }
        }

        findPreference<Preference>("workManagerStatusButton")?.setOnPreferenceClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionToWorkManagerFragment())
            true
        }
    }
}
