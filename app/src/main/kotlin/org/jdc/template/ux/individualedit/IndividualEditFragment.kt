package org.jdc.template.ux.individualedit

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.ComposeFragment

@AndroidEntryPoint
class IndividualEditFragment : ComposeFragment() {
    @Composable
    override fun ComposeScreen() {
        IndividualEditScreen()
    }
}