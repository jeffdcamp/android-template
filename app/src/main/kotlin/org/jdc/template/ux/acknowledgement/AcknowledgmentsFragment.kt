package org.jdc.template.ux.acknowledgement

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.ComposeFragment

@AndroidEntryPoint
class AcknowledgmentsFragment : ComposeFragment() {
    @Composable
    override fun ComposeScreen(navController: NavController) = AcknowledgementScreen(navController)
}