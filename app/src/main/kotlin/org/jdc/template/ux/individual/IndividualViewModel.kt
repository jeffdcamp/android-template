package org.jdc.template.ux.individual

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.squareup.inject.assisted.Assisted
import com.vikingsen.inject.viewmodel.ViewModelInject
import kotlinx.coroutines.launch
import org.jdc.template.Analytics
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.viewmodel.BaseViewModel

class IndividualViewModel
@ViewModelInject constructor(
        private val analytics: Analytics,
        private val individualRepository: IndividualRepository,
        @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<IndividualViewModel.Event>() {

    private val individualId = requireNotNull(savedStateHandle.get<Long>("individualId")) { "individualId cannot be null" }
    val individualFlow = individualRepository.getIndividualFlow(individualId)

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    fun deleteIndividual() = viewModelScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)

        sendEvent(Event.IndividualDeletedEvent)
    }

    fun editIndividual() {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        sendEvent(Event.EditIndividualEvent(individualId))
    }

    sealed class Event {
        data class EditIndividualEvent(val individualId: Long) : Event()
        object IndividualDeletedEvent : Event()
    }
}