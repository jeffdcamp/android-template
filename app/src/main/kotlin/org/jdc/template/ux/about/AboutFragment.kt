package org.jdc.template.ux.about

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.ComposeFragment

@AndroidEntryPoint
class AboutFragment : ComposeFragment() {

    @Composable
    override fun ComposeScreen(navController: NavController) = AboutScreen(navController)
}
