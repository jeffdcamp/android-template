package org.jdc.template.ux.directory

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.model.db.main.individual.IndividualDao
import org.jdc.template.model.repository.IndividualRepository
import javax.inject.Inject

class DirectoryViewModel
@Inject constructor(
    private val individualRepository: IndividualRepository
) : ViewModel() {

    val onNewIndividualEvent = SingleLiveEvent<Void>()
    val showIndividualEvent = SingleLiveEvent<Long>()

    val directoryList: LiveData<List<IndividualDao.DirectoryListItem>>

    init {
        directoryList = loadDirectoryList()
    }

    private fun loadDirectoryList(): LiveData<List<IndividualDao.DirectoryListItem>> {
        return individualRepository.getDirectoryListLiveData()
    }

    fun addIndividual() {
        onNewIndividualEvent.call()
    }

    fun onDirectoryIndividualClicked(directoryListItem: IndividualDao.DirectoryListItem) {
        showIndividualEvent.value = directoryListItem.id
    }
}