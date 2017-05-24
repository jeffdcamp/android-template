package org.jdc.template.ux.directory

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.inject.Injector
import javax.inject.Inject

class DirectoryViewModel constructor(application: Application): AndroidViewModel(application) {

    @Inject
    lateinit var individualDao: IndividualDao

    init {
        Injector.get().inject(this)
    }

    fun getDirectoryListItemsLive() = individualDao.findAllDirectListItemsLive()
}