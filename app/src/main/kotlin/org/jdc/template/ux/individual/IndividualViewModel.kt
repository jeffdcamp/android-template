package org.jdc.template.ux.individual

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.squareup.inject.assisted.Assisted
import com.vikingsen.inject.viewmodel.ViewModelInject
import kotlinx.coroutines.launch
import org.jdc.template.Analytics
import org.jdc.template.livedata.EmptySingleLiveEvent
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.viewmodel.BaseViewModel

class IndividualViewModel
@ViewModelInject constructor(
        private val analytics: Analytics,
        private val individualRepository: IndividualRepository,
        @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val individualId = requireNotNull(savedStateHandle.get<Long>("individualId")) { "individualId cannot be null" }
    val individual: LiveData<Individual> = individualRepository.getIndividualLiveData(individualId)

    val onEditIndividualEvent = SingleLiveEvent<Long>() // individualId
    val onIndividualDeletedEvent = EmptySingleLiveEvent()

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    fun deleteIndividual() = launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)

        onIndividualDeletedEvent.postCall()
    }

    fun editIndividual() {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        onEditIndividualEvent.value = individualId
    }
}