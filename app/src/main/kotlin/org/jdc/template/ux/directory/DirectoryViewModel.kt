package org.jdc.template.ux.directory

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import org.jdc.template.coroutine.channel.ViewModelChannel
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.model.repository.IndividualRepository

class DirectoryViewModel
@ViewModelInject constructor(
    private val individualRepository: IndividualRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _eventChannel: ViewModelChannel<Event> = ViewModelChannel(this)
    val eventChannel: ReceiveChannel<Event> = _eventChannel

    val directoryListFlow: Flow<List<DirectoryItem>> // change to ShareFlow.shareIn(viewModelScope) when available (in coroutine library https://github.com/Kotlin/kotlinx.coroutines/issues/2069)
        get() = individualRepository.getDirectoryListFlow()

    fun addIndividual() {
        _eventChannel.sendAsync(Event.NewIndividualEvent)
    }

    fun onDirectoryIndividualClicked(directoryListItem: DirectoryItem) {
        _eventChannel.sendAsync(Event.ShowIndividualEvent(directoryListItem.id))
    }

    sealed class Event {
        object NewIndividualEvent : Event()
        data class ShowIndividualEvent(val individualId: Long) : Event()
    }
}