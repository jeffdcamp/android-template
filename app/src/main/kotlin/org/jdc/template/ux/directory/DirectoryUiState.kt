package org.jdc.template.ux.directory

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.model.db.main.directoryitem.DirectoryItem

data class DirectoryUiState(
    // Data
    val directoryListFlow: StateFlow<List<DirectoryItem>> = MutableStateFlow(emptyList()),

    // Events
    val onNewClicked: () -> Unit = {},
    val onIndividualClicked: (individualId: String) -> Unit = {},
    val onSettingsClicked: () -> Unit = {}
)