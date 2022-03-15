package org.jdc.template.ux.acknowledgement

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.SimpleComposeFragment

@AndroidEntryPoint
class AcknowledgmentsFragment : SimpleComposeFragment() {
    @Composable
    override fun ContentScreen() {
        AcknowledgementScreen()
    }
}