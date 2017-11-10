package org.jdc.template.ux.individualedit

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.livedata.AbsentLiveData
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.util.CoroutineContextProvider
import javax.inject.Inject

class IndividualEditViewModel
@Inject constructor(private val cc: CoroutineContextProvider,
                    private val individualDao: IndividualDao) : ViewModel() {

    private val individualId = MutableLiveData<Long>()
    val individual: LiveData<Individual>

    val onIndividualSavedEvent = SingleLiveEvent<Void>()

    init {
        individual = AbsentLiveData.switchMap(individualId) {
            loadIndividual(it)
        }
    }

    fun setIndividualId(individualId: Long) {
        if (individualId != this.individualId.value) {
            this.individualId.value = individualId
        }
    }

    private fun loadIndividual(individualId: Long): LiveData<Individual> {
        return individualDao.findByIdLive(individualId)
    }

    fun saveIndividual() {
        individual.value?.let {
            launch(cc.ui) {
                run(coroutineContext + cc.commonPool) {
                    if (it.id <= 0) {
                        individualDao.insert(it)
                    } else {
                        individualDao.update(it)
                    }
                }

                onIndividualSavedEvent.call()
            }
        }
    }
}