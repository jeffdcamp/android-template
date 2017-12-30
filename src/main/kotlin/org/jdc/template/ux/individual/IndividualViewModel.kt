package org.jdc.template.ux.individual

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.analytics.HitBuilders
import kotlinx.coroutines.experimental.launch
import org.jdc.template.Analytics
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.livedata.AbsentLiveData
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.util.CoroutineContextProvider
import javax.inject.Inject

class IndividualViewModel
@Inject constructor(
        private val cc: CoroutineContextProvider,
        private val analytics: Analytics,
        private val individualDao: IndividualDao
) : ViewModel() {

    private val individualId = MutableLiveData<Long>()
    val individual: LiveData<Individual>
    val onEditIndividualEvent = SingleLiveEvent<Long>() // individualId
    val onIndividualDeletedEvent = SingleLiveEvent<Void>()

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
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_VIEW).build())
        return individualDao.findByIdLiveData(individualId)
    }

    fun deleteTask() {
        individualId.value?.let { id ->
            launch(cc.commonPool) {
                analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_DELETE).build())
                individualDao.deleteById(id)

                onIndividualDeletedEvent.postCall()
            }
        }
    }

    fun editTask() {
        onEditIndividualEvent.value = individualId.value
    }
}