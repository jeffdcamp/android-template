package org.jdc.template.ux.directory

import androidx.lifecycle.LiveData
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.model.db.main.individual.IndividualDao
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