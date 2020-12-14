package org.jdc.template.ux.individual

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.jdc.template.analytics.Analytics
import org.jdc.template.coroutine.channel.ViewModelChannel
import org.jdc.template.delegates.requireSavedState
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository

class IndividualViewModel
@ViewModelInject constructor(
    private val analytics: Analytics,
    private val individualRepository: IndividualRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _eventChannel: ViewModelChannel<Event> = ViewModelChannel(this)
    val eventChannel: ReceiveChannel<Event> = _eventChannel

    private val individualId: Long by requireSavedState(savedStateHandle, "individualId")
    val individualFlow: Flow<Individual>
        get() = individualRepository.getIndividualFlow(individualId)

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    fun deleteIndividual() = viewModelScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)

        _eventChannel.sendAsync(Event.IndividualDeletedEvent)
    }

    fun editIndividual() {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        _eventChannel.sendAsync(Event.EditIndividualEvent(individualId))
    }

    sealed class Event {
        data class EditIndividualEvent(val individualId: Long) : Event()
        object IndividualDeletedEvent : Event()
    }
}