package org.jdc.template.ux.directory

import androidx.lifecycle.LiveData
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.viewmodel.BaseViewModel
import org.jdc.template.util.CoroutineContextProvider
import javax.inject.Inject

class DirectoryViewModel
@Inject constructor(
    cc: CoroutineContextProvider,
    private val individualRepository: IndividualRepository
) : BaseViewModel(cc) {

    val onNewIndividualEvent = SingleLiveEvent<Void>()
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