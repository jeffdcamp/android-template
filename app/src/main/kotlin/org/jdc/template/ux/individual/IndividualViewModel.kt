package org.jdc.template.ux.individual

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.analytics.Analytics
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.compose.dialog.MessageDialogData
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
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
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    private val individualId: String by requireSavedState(savedStateHandle, IndividualRoute.Arg.INDIVIDUAL_ID)
    val individualFlow: StateFlow<Individual?> = individualRepository.getIndividualFlow(individualId).stateInDefault(viewModelScope, null)

    private val _deleteIndividualDialogDataFlow = MutableStateFlow(MessageDialogData())
    val deleteIndividualDialogDataFlow: StateFlow<MessageDialogData> = _deleteIndividualDialogDataFlow.asStateFlow()

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    fun onDeleteClicked() {
        _deleteIndividualDialogDataFlow.value = MessageDialogData(true, text = application.getString(R.string.delete_individual_confirm))
    }

    fun deleteIndividual() = viewModelScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)
        popBackStack()
    }

    fun editIndividual() {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        navigate(IndividualEditRoute.createRoute(individualId))
    }

    fun dismissDeleteIndividualDialog() {
        _deleteIndividualDialogDataFlow.value = MessageDialogData()
    }
}