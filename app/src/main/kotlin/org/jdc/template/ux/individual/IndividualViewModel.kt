package org.jdc.template.ux.individual

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.analytics.Analytics
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.BaseViewModel
import org.jdc.template.ui.compose.dialog.MessageDialogData
import org.jdc.template.util.delegates.requireSavedState
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.ux.individualedit.IndividualEditRoute
import javax.inject.Inject

@HiltViewModel
class IndividualViewModel
@Inject constructor(
    private val application: Application,
    private val analytics: Analytics,
    private val individualRepository: IndividualRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val individualId: String by requireSavedState(savedStateHandle)
    val individualFlow: StateFlow<Individual?> = individualRepository.getIndividualFlow(individualId).stateInDefault(viewModelScope, null)

    private val _messageDialogDataFlow = MutableStateFlow(MessageDialogData())
    val messageDialogDataFlow: StateFlow<MessageDialogData> = _messageDialogDataFlow

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    fun onDeleteClicked() {
        _messageDialogDataFlow.value = MessageDialogData(true, text = application.getString(R.string.delete_individual_confirm))
    }

    fun deleteIndividual() = viewModelScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)
    }

    fun editIndividual() {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        navigate(IndividualEditRoute.createRoute(individualId))
    }

    fun hideInfoDialog() {
        _messageDialogDataFlow.value = MessageDialogData()
    }
}