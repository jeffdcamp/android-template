package org.jdc.template.ux.individual

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.jdc.template.Analytics
import org.jdc.template.delegates.requireSavedState
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository

class IndividualViewModel
@ViewModelInject constructor(
    private val analytics: Analytics,
    private val individualRepository: IndividualRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    val individualId: Long by requireSavedState(savedStateHandle, "individualId")
    val individualFlow: Flow<Individual>
        get() = individualRepository.getIndividualFlow(individualId)

    var confirmDeleteState by mutableStateOf(false)

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    fun deleteIndividual() = GlobalScope.launch(Dispatchers.IO) {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)
        confirmDeleteState = false
    }
}