package org.jdc.template.ux.directory

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.squareup.inject.assisted.Assisted
import com.vikingsen.inject.viewmodel.ViewModelInject
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.viewmodel.BaseViewModel

class DirectoryViewModel
@ViewModelInject constructor(
        private val individualRepository: IndividualRepository,
        @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<DirectoryViewModel.Event>() {

    fun getDirectoryList(): LiveData<List<DirectoryItem>> {
        return individualRepository.getDirectoryListFlow().asLiveData()
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