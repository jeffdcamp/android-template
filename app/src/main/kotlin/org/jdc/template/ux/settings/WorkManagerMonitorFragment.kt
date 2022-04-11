package org.jdc.template.ux.settings

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import org.dbtools.android.work.ux.monitor.WorkManagerStatusScreen
import org.jdc.template.ui.compose.ComposeFragment

@AndroidEntryPoint
class WorkManagerMonitorFragment : ComposeFragment() {
    @Composable
    override fun ComposeScreen(navController: NavController) {
        WorkManagerStatusScreen(onBack = { navController.popBackStack() })
    }
}
