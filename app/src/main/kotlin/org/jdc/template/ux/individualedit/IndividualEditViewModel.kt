package org.jdc.template.ux.individualedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.util.coroutine.channel.ViewModelChannel
import org.jdc.template.util.delegates.requireSavedState
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class IndividualEditViewModel
@Inject constructor(
    private val individualRepository: IndividualRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _eventChannel: ViewModelChannel<Event> = ViewModelChannel(this)
    val eventChannel: ReceiveChannel<Event> = _eventChannel

    private val individualId: String by requireSavedState(savedStateHandle)
    private var individual = Individual()
    var loadedIndividualFlow: Flow<Individual> = flow {
        individualRepository.getIndividual(individualId)?.let { individual ->
            this@IndividualEditViewModel.individual = individual
            emit(individual)
            _birthDateTextFlow.value = individual.birthDate
            _alarmTimeTextFlow.value = individual.alarmTime
        }
    }

    private val _birthDateTextFlow = MutableStateFlow<LocalDate?>(null)
    val birthDateTextFlow: Flow<LocalDate> = _birthDateTextFlow.asStateFlow().filterNotNull()

    private val _alarmTimeTextFlow = MutableStateFlow<LocalTime?>(null)
    val alarmTimeTextFlow: Flow<LocalTime> = _alarmTimeTextFlow.asStateFlow().filterNotNull()

    fun saveIndividual(firstName: String, lastName: String, phone: String, email: String) = viewModelScope.launch {
        individual.firstName = valueOrNull(firstName)
        individual.lastName = valueOrNull(lastName)
        individual.phone = valueOrNull(phone)
        individual.email = valueOrNull(email)

        // changed via setBirthDate and setAlarmTime
        // individual.birthDate = birthDate
        // individual.alarmTime = alarmTime

        if (!validate()) {
            return@launch
        }

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
        if (individual.firstName.isNullOrBlank()) {
            _eventChannel.sendAsync(Event.ValidationSaveError(FieldValidationError.FIRST_NAME_REQUIRED))
            return false
        }

        return true
    }

    fun onBirthDateClicked() {
        _eventChannel.sendAsync(Event.ShowBirthDateSelection(individual.birthDate ?: LocalDate.now()))
    }

    fun onAlarmTimeClicked() {
        _eventChannel.sendAsync(Event.ShowAlarmTimeSelection(individual.alarmTime ?: LocalTime.now()))
    }

    fun setBirthDate(birthDate: LocalDate) {
        individual.birthDate = birthDate
        _birthDateTextFlow.value = birthDate
    }

    fun setAlarmTime(alarmTime: LocalTime) {
        individual.alarmTime = alarmTime
        _alarmTimeTextFlow.value = alarmTime
    }

    enum class FieldValidationError(val errorMessageId: Int) {
        FIRST_NAME_REQUIRED(R.string.required),
    }

    sealed class Event {
        object IndividualSaved : Event()
        class ShowBirthDateSelection(val date: LocalDate) : Event()
        class ShowAlarmTimeSelection(val time: LocalTime) : Event()
        class ValidationSaveError(val error: FieldValidationError) : Event()
    }
}