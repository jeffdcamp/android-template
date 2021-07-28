package org.jdc.template.ux.individualedit

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.compose.SimpleDialogData
import org.jdc.template.util.coroutine.channel.ViewModelChannel
import org.jdc.template.util.delegates.requireSavedState
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class IndividualEditViewModel
@Inject constructor(
    private val application: Application,
    private val individualRepository: IndividualRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _eventChannel: ViewModelChannel<Event> = ViewModelChannel(this)
    val eventChannel: ReceiveChannel<Event> = _eventChannel

    private val individualId: String by requireSavedState(savedStateHandle)
    private var individual = Individual()

    // hold state for Compose views
    private val _firstNameFlow = MutableStateFlow("")
    val firstNameFlow: StateFlow<String> = _firstNameFlow

    private val _lastNameFlow = MutableStateFlow("")
    val lastNameFlow: StateFlow<String> = _lastNameFlow

    private val _phoneNumberFlow = MutableStateFlow("")
    val phoneNumberFlow: StateFlow<String> = _phoneNumberFlow

    private val _emailFlow = MutableStateFlow("")
    val emailFlow: StateFlow<String> = _emailFlow

    private val _birthDateFlow = MutableStateFlow<LocalDate?>(null)
    val birthDateFlow: Flow<LocalDate?> = _birthDateFlow

    private val _alarmTimeFlow = MutableStateFlow<LocalTime?>(null)
    val alarmTimeFlow: Flow<LocalTime?> = _alarmTimeFlow

    private val _simpleDialogData = MutableStateFlow(SimpleDialogData())
    val simpleDialogData: StateFlow<SimpleDialogData> = _simpleDialogData

    init {
        loadIndividual()
    }

    private fun loadIndividual() = viewModelScope.launch {
        individualRepository.getIndividual(individualId)?.let { individual ->
            this@IndividualEditViewModel.individual = individual
            _firstNameFlow.value = individual.firstName ?: ""
            _lastNameFlow.value = individual.lastName ?: ""
            _phoneNumberFlow.value = individual.phone ?: ""
            _emailFlow.value = individual.email ?: ""
            _birthDateFlow.value = individual.birthDate
            _alarmTimeFlow.value = individual.alarmTime
        }
    }

    fun saveIndividual() = viewModelScope.launch {
        if (!validate()) {
            return@launch
        }

        individual.firstName = valueOrNull(_firstNameFlow.value)
        individual.lastName = valueOrNull(_lastNameFlow.value)
        individual.phone = valueOrNull(_phoneNumberFlow.value)
        individual.email = valueOrNull(_emailFlow.value)
        individual.birthDate = _birthDateFlow.value
        individual.alarmTime = _alarmTimeFlow.value

        individualRepository.saveIndividual(individual)

        _eventChannel.sendAsync(Event.IndividualSaved)
    }

    private fun valueOrNull(value: String): String? {
        return if (value.isBlank()) {
            null
        } else {
            value
        }
    }

    private fun validate(): Boolean {
        if (_firstNameFlow.value.isBlank()) {
            val text = application.getString(R.string.x_required, application.getString(R.string.first_name))
            _simpleDialogData.value = SimpleDialogData(true, application.getString(R.string.error), text)
            return false
        }

        return true
    }

    fun hideInfoDialog() {
        _simpleDialogData.value = SimpleDialogData()
    }

    fun setFirstName(value: String) {
        _firstNameFlow.value = value
    }

    fun setLastName(value: String) {
        _lastNameFlow.value = value
    }

    fun setPhoneNumber(value: String) {
        _phoneNumberFlow.value = value
    }

    fun setEmail(value: String) {
        _emailFlow.value = value
    }

    fun setBirthDate(birthDate: LocalDate?) {
        _birthDateFlow.value = birthDate
    }

    fun setAlarmTime(alarmTime: LocalTime?) {
        _alarmTimeFlow.value = alarmTime
    }

    fun onBirthDateClicked() {
        _eventChannel.sendAsync(Event.ShowBirthDateSelection(_birthDateFlow.value ?: LocalDate.now()))
    }

    fun onAlarmTimeClicked() {
        _eventChannel.sendAsync(Event.ShowAlarmTimeSelection(_alarmTimeFlow.value ?: LocalTime.now()))
    }

    sealed class Event {
        object IndividualSaved : Event()
        class ShowBirthDateSelection(val date: LocalDate) : Event()
        class ShowAlarmTimeSelection(val time: LocalTime) : Event()
    }
}