package org.jdc.template.ux.individual

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.ui.compose.dialog.MessageDialogData

data class IndividualUiState(
    // Data
    val individualFlow: StateFlow<Individual?> = MutableStateFlow(null),

    // Events
    val onEdit: () -> Unit = {},
    val onDelete: () -> Unit = {},
    val deleteIndividual: () -> Unit = {},

    // Dialog - Delete
    val deleteIndividualDialogDataFlow: StateFlow<MessageDialogData?> = MutableStateFlow(null),
    val dismissDeleteIndividualDialog: () -> Unit = {},
)