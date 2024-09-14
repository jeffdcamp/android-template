package org.jdc.template.ux.directory

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.model.db.main.directoryitem.DirectoryItemEntityView
import org.jdc.template.model.domain.inline.IndividualId

data class DirectoryUiState(
    // Data
    val directoryListFlow: StateFlow<List<DirectoryItemEntityView>> = MutableStateFlow(emptyList()),

    // Events
    val onNewClick: () -> Unit = {},
    val onIndividualClick: (individualId: IndividualId) -> Unit = {},
    val onSettingsClick: () -> Unit = {}
)