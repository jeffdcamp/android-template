package org.jdc.template.ux.directory

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.SimpleComposeFragment

@AndroidEntryPoint
class DirectoryFragment : SimpleComposeFragment() {
    @Composable
    override fun ContentScreen() {
        DirectoryScreen()
    }
}
