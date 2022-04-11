package org.jdc.template.ux.directory

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.ComposeFragment

@AndroidEntryPoint
class DirectoryFragment : ComposeFragment() {
    @Composable
    override fun ComposeScreen(navController: NavController) = DirectoryScreen(navController)
}
