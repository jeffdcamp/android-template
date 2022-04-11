package org.jdc.template.ux.individualedit

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.ui.compose.ComposeFragment

@AndroidEntryPoint
class IndividualEditFragment : ComposeFragment() {
    @Composable
    override fun ComposeScreen(navController: NavController) = IndividualEditScreen(navController)
}