package org.jdc.template.ux.individual

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.analytics.Analytics
import org.jdc.template.shared.model.domain.Individual
import org.jdc.template.shared.model.domain.inline.IndividualId
import org.jdc.template.shared.model.repository.IndividualRepository
import org.jdc.template.shared.util.ext.stateInDefault
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.ui.compose.dialog.showMessageDialog
import org.jdc.template.ui.navigation.ViewModelNavigation3
import org.jdc.template.ui.navigation.ViewModelNavigation3Impl
import org.jdc.template.ux.individualedit.IndividualEditRoute

class IndividualViewModel(
    private val individualRepository: IndividualRepository,
    private val analytics: Analytics,
    private val applicationScope: CoroutineScope,
    individualRoute: IndividualRoute,
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?>
        field = MutableStateFlow<DialogUiState<*>?>(null)

    val uiStateFlow: StateFlow<IndividualUiState> = individualRepository.getIndividualFlow(individualRoute.individualId).map { individual: Individual? ->
        if (individual != null) {
            IndividualUiState.Ready(individual)
        } else {
            IndividualUiState.Empty
        }
    }.stateInDefault(viewModelScope, IndividualUiState.Loading)

    fun onDeleteClick(individualId: IndividualId) = viewModelScope.launch {
        showMessageDialog(
            dialogUiStateFlow = dialogUiStateFlow,
            text = { stringResource(R.string.delete_individual_confirm) },
            onConfirm = { deleteIndividual(individualId) },
            onDismiss = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun deleteIndividual(individualId: IndividualId) = applicationScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)
        popBackStack()
    }

    fun onEditClick(individualId: IndividualId) {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        navigate(IndividualEditRoute(individualId))
    }
}

sealed interface IndividualUiState {
    data object Loading : IndividualUiState

    data class Ready(
        val individual: Individual,
    ) : IndividualUiState

    data object Empty : IndividualUiState
}
