package org.jdc.template.ux.directory

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.livedata.SingleLiveEvent
import javax.inject.Inject

class DirectoryViewModel
@Inject constructor(private val individualDao: IndividualDao) : ViewModel() {

    val onNewIndividualEvent = SingleLiveEvent<Void>()

    val directoryList: LiveData<List<IndividualDao.DirectoryListItem>>

    init {
        directoryList = loadDirectoryList()
    }

    private fun loadDirectoryList(): LiveData<List<IndividualDao.DirectoryListItem>> {
        return individualDao.findAllDirectListItemsLiveData()
    }

    fun addIndividual() {
        onNewIndividualEvent.call()
    }
}