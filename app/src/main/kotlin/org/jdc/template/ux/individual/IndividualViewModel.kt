package org.jdc.template.ux.individual

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.analytics.HitBuilders
import kotlinx.coroutines.experimental.launch
import org.jdc.template.Analytics
import org.jdc.template.livedata.AbsentLiveData
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.viewmodel.BaseViewModel
import org.jdc.template.util.CoroutineContextProvider
import javax.inject.Inject

class IndividualViewModel
@Inject constructor(
        cc: CoroutineContextProvider,
        private val analytics: Analytics,
        private val individualRepository: IndividualRepository
) : BaseViewModel(cc) {

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
        return individualRepository.getIndividualLiveData(individualId)
    }

    fun deleteTask() {
        individualId.value?.let { id ->
            launch {
                analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_DELETE).build())
                individualRepository.deleteIndividual(id)

                onIndividualDeletedEvent.postCall()
            }
        }
    }

    fun editTask() {
        onEditIndividualEvent.value = individualId.value
    }
}