package org.jdc.template.ux.directory

import androidx.lifecycle.SavedStateHandle
import com.squareup.inject.assisted.Assisted
import com.vikingsen.inject.viewmodel.ViewModelInject
import kotlinx.coroutines.flow.Flow
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.viewmodel.BaseViewModel

class DirectoryViewModel
@ViewModelInject constructor(
        private val individualRepository: IndividualRepository,
        @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<DirectoryViewModel.Event>() {

    fun getDirectoryList(): Flow<List<DirectoryItem>> {
        return individualRepository.getDirectoryListFlow()
    }

    fun addIndividual() {
        sendEvent(Event.NewIndividualEvent)
    }

    fun onDirectoryIndividualClicked(directoryListItem: DirectoryItem) {
        sendEvent(Event.ShowIndividualEvent(directoryListItem.id))
    }

    sealed class Event {
        object NewIndividualEvent : Event()
        data class ShowIndividualEvent(val individualId: Long) : Event()
    }
}