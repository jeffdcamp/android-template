package org.jdc.template.ux.individual

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.ui.compose.dialog.DialogUiState

data class IndividualUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val individualFlow: StateFlow<Individual?> = MutableStateFlow(null),

    // Events
    val onEdit: () -> Unit = {},
    val onDelete: () -> Unit = {},
    val deleteIndividual: () -> Unit = {},
)