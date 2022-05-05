package org.jdc.template.ux.acknowledgement

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AcknowledgementUiState(
    val acknowledgementHtmlFlow: StateFlow<String?> = MutableStateFlow(null)
)
