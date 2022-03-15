package org.jdc.template.ux.individualedit

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.SimpleComposeFragment

@AndroidEntryPoint
class IndividualEditFragment : SimpleComposeFragment() {
    @Composable
    override fun ContentScreen() {
        IndividualEditScreen()
    }
}