package org.jdc.template.ux.about

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.SimpleComposeFragment

@AndroidEntryPoint
class AboutFragment : SimpleComposeFragment() {
    @Composable
    override fun ContentScreen() {
        AboutScreen()
    }
}
