package org.jdc.template.ux.individual

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.SimpleComposeFragment

@AndroidEntryPoint
class IndividualFragment : SimpleComposeFragment() {
    @Composable
    override fun ContentScreen() {
        IndividualScreen()
    }
}