package org.jdc.template.ux.directory

import android.arch.lifecycle.ViewModel
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.livedata.SingleLiveEvent
import javax.inject.Inject

class DirectoryViewModel
@Inject constructor(private val individualDao: IndividualDao): ViewModel() {

    val onNewIndividualEvent = SingleLiveEvent<Void>()

    fun getDirectoryListItemsLive() = individualDao.findAllDirectListItemsLive()

    fun addIndividual() {
        onNewIndividualEvent.call()
    }
}