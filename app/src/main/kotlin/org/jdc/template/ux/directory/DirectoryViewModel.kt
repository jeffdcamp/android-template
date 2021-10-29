package org.jdc.template.ux.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.util.coroutine.channel.ViewModelChannel
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individualedit.IndividualEditRoute
import javax.inject.Inject

@HiltViewModel
class DirectoryViewModel
@Inject constructor(
    individualRepository: IndividualRepository
) : ViewModel() {
    private val _eventChannel: ViewModelChannel<Event> = ViewModelChannel(this)
    val eventChannel: ReceiveChannel<Event> = _eventChannel

    val directoryListFlow: StateFlow<List<DirectoryItem>> = individualRepository.getDirectoryListFlow().stateInDefault(viewModelScope, emptyList())

    fun addIndividual() {
        _eventChannel.sendAsync(Event.Navigate(IndividualEditRoute.createRoute()))
    }

    fun onDirectoryIndividualClicked(directoryListItem: DirectoryItem) {
        _eventChannel.sendAsync(Event.Navigate(IndividualRoute.createRoute(directoryListItem.individualId)))
    }

    sealed class Event {
        class Navigate(val route: String) : Event()
    }
}