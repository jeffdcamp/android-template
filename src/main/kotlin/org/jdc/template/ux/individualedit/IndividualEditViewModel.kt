package org.jdc.template.ux.individualedit

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.launch
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.util.CoroutineContextProvider
import javax.inject.Inject

class IndividualEditViewModel
@Inject constructor(
        private val cc: CoroutineContextProvider,
        private val individualDao: IndividualDao
) : ViewModel() {

    var individual = Individual()

    val onIndividualLoadedEvent = SingleLiveEvent<Void>()
    val onIndividualSavedEvent = SingleLiveEvent<Void>()

    fun loadIndividual(individualId: Long) = launch(cc.commonPool) {
        individualDao.findById(individualId)?.let {
            individual = it

            onIndividualLoadedEvent.postCall()
        }
    }

    fun saveIndividual() = launch(cc.commonPool) {
        if (individual.id <= 0) {
            individualDao.insert(individual)
        } else {
            individualDao.update(individual)
        }

        onIndividualSavedEvent.postCall()
    }
}