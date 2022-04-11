package org.jdc.template.ux.individual

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.ComposeFragment

@AndroidEntryPoint
class IndividualFragment : ComposeFragment() {
    @Composable
    override fun ComposeScreen(navController: NavController) = IndividualScreen(navController)
}