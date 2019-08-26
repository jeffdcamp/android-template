package org.jdc.template.ux.directory

import androidx.lifecycle.LiveData
import com.vikingsen.inject.viewmodel.ViewModelInject
import org.jdc.template.livedata.EmptySingleLiveEvent
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.viewmodel.BaseViewModel

class DirectoryViewModel
@ViewModelInject constructor(
    private val individualRepository: IndividualRepository
) : BaseViewModel() {

    val onNewIndividualEvent = EmptySingleLiveEvent()
    val showIndividualEvent = SingleLiveEvent<Long>()

    val directoryList: LiveData<List<DirectoryItem>>

    init {
        directoryList = loadDirectoryList()
    }

    private fun loadDirectoryList(): LiveData<List<DirectoryItem>> {
        return individualRepository.getDirectoryListLiveData()
    }

    fun addIndividual() {
        onNewIndividualEvent.call()
    }

    fun onDirectoryIndividualClicked(directoryListItem: DirectoryItem) {
        showIndividualEvent.value = directoryListItem.id
    }
}