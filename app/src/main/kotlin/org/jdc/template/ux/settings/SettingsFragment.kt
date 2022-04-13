package org.jdc.template.ux.settings

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.ComposeFragment

@AndroidEntryPoint
class SettingsFragment : ComposeFragment() {
    @Composable
    override fun ComposeScreen(navController: NavController) = SettingsScreen(navController)
}