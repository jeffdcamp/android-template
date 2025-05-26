package org.jdc.template.ux.individual

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.shared.model.domain.Individual
import org.jdc.template.ui.compose.dialog.DialogUiState

data class IndividualUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val individualFlow: StateFlow<Individual?> = MutableStateFlow(null),

    // Events
    val onEditClick: () -> Unit = {},
    val onDeleteClick: () -> Unit = {},
    val deleteIndividual: () -> Unit = {},
)