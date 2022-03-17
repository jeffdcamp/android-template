package org.jdc.template.ux.acknowledgement

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.ComposeFragment

@AndroidEntryPoint
class AcknowledgmentsFragment : ComposeFragment() {
    @Composable
    override fun ComposeScreen() {
        AcknowledgementScreen()
    }
}