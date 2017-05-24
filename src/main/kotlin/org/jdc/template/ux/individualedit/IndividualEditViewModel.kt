package org.jdc.template.ux.individualedit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.inject.Injector
import javax.inject.Inject

class IndividualEditViewModel constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var individualDao: IndividualDao

    var individual: Individual? = null

    init {
        Injector.get().inject(this)
    }

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