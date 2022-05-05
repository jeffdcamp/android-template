package org.jdc.template.ux.about

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AboutUiState(
    // Data
    val resetServiceEnabledFlow: StateFlow<Boolean> = MutableStateFlow(false),

    // Events
    val testQueryWebServiceCall: () -> Unit = {},
    val testFullUrlQueryWebServiceCall: () -> Unit = {},
    val testSaveQueryWebServiceCall: () -> Unit = {},
    val workManagerSimpleTest: () -> Unit = {},
    val workManagerSyncTest: () -> Unit = {},
    val testTableChange: () -> Unit = {},
    val licensesClicked: () -> Unit = {},
    val createSampleData: () -> Unit = {},
)
