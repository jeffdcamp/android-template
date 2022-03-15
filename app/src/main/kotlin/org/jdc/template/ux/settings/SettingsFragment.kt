package org.jdc.template.ux.settings

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.SimpleComposeFragment

@AndroidEntryPoint
class SettingsFragment : SimpleComposeFragment() {
    @Composable
    override fun ContentScreen() {
        SettingsScreen()
    }
}
