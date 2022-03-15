package org.jdc.template.ux.settings

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import org.dbtools.android.work.ux.monitor.WorkManagerStatusScreen
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.ui.compose.SimpleComposeFragment

@AndroidEntryPoint
class WorkManagerMonitorFragment : SimpleComposeFragment() {
    @Composable
    override fun ContentScreen() {
        val navController = LocalNavController.current
        WorkManagerStatusScreen(onBack = { navController?.popBackStack() })
    }
}
