package org.jdc.template.ux.individual

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.analytics.Analytics
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.compose.SimpleDialogData
import org.jdc.template.util.coroutine.channel.ViewModelChannel
import org.jdc.template.util.delegates.requireSavedState
import javax.inject.Inject

@HiltViewModel
class IndividualViewModel
@Inject constructor(
    private val application: Application,
    private val analytics: Analytics,
    private val individualRepository: IndividualRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _eventChannel: ViewModelChannel<Event> = ViewModelChannel(this)
    val eventChannel: ReceiveChannel<Event> = _eventChannel

    private val individualId: Long by requireSavedState(savedStateHandle)
    val individualFlow: Flow<Individual>
        get() = individualRepository.getIndividualFlow(individualId)

    private val _simpleDialogData = MutableStateFlow(SimpleDialogData())
    val simpleDialogData: StateFlow<SimpleDialogData> = _simpleDialogData

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    fun onDeleteClicked() {
        _simpleDialogData.value = SimpleDialogData(true, text = application.getString(R.string.delete_individual_confirm))
    }

    fun deleteIndividual() = viewModelScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)
    }

    fun editIndividual() {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        _eventChannel.sendAsync(Event.Navigate(IndividualFragmentDirections.actionToIndividualEditFragment(individualId)))
    }

    fun hideInfoDialog() {
        _simpleDialogData.value = SimpleDialogData()
    }

    sealed class Event {
        class Navigate(val direction: NavDirections) : Event()
    }
}