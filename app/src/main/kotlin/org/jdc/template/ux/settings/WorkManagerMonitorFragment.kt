package org.jdc.template.ux.settings

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import org.dbtools.android.work.ux.monitor.WorkManagerStatusScreen
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.compose.ComposeFragment

@AndroidEntryPoint
class WorkManagerMonitorFragment : ComposeFragment() {
    @Composable
    override fun ComposeScreen() {
        val navController = LocalNavController.current
        WorkManagerStatusScreen(onBack = { navController?.popBackStack() })
    }
}
