package org.jdc.template.ux.individualedit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.individual.IndividualDao
import javax.inject.Inject

class IndividualEditViewModel
@Inject constructor(application: Application,
                    private val individualDao: IndividualDao) : AndroidViewModel(application) {

    var individual: Individual? = null

    fun loadIndividual(individualId: Long): Individual? {
        individual?.let { return it }


        individualDao.findById(individualId)?.let {
            individual = it
        }

        return individual
    }

    fun saveIndividual() {
        individual?.let {
            individualDao.update(it)
        }
    }
}