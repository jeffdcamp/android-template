package org.jdc.template.ux.individual

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.ComposeFragment

@AndroidEntryPoint
class IndividualFragment : ComposeFragment() {
    @Composable
    override fun ComposeScreen() = IndividualScreen()
}