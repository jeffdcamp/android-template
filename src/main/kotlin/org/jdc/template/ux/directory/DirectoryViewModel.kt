package org.jdc.template.ux.directory

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import org.jdc.template.datasource.database.main.individual.IndividualDao
import javax.inject.Inject

class DirectoryViewModel
@Inject constructor(application: Application,
                    private val individualDao: IndividualDao): AndroidViewModel(application) {

    fun getDirectoryListItemsLive() = individualDao.findAllDirectListItemsLive()
}