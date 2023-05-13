package org.jdc.template.ux.individual

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.analytics.Analytics
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.ui.compose.dialog.showMessageDialog
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
import org.jdc.template.util.ext.requireValueClass
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.ux.individualedit.IndividualEditRoute
import javax.inject.Inject

@HiltViewModel
class IndividualViewModel
@Inject constructor(
    private val analytics: Analytics,
    private val individualRepository: IndividualRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    private val individualId: IndividualId = savedStateHandle.requireValueClass<String, IndividualId>(IndividualRoute.Arg.INDIVIDUAL_ID) { IndividualId(it) }

    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)

    val uiState: IndividualUiState = IndividualUiState(
        dialogUiStateFlow = dialogUiStateFlow,

        individualFlow = individualRepository.getIndividualFlow(individualId).stateInDefault(viewModelScope, null),

        onEdit = { editIndividual() },
        onDelete = { onDeleteClicked() },
        deleteIndividual = { deleteIndividual() },
    )

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    private fun onDeleteClicked() {
        showMessageDialog(dialogUiStateFlow, text = { stringResource(R.string.delete_individual_confirm) }, onConfirm = { deleteIndividual() }, onDismiss = { dismissDialog(dialogUiStateFlow) })
    }

    private fun deleteIndividual() = viewModelScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)
        popBackStack()
    }

    private fun editIndividual() {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        navigate(IndividualEditRoute.createRoute(individualId))
    }
}