package org.jdc.template.ux.individualedit

import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import org.jdc.template.Analytics
import org.jdc.template.R
import org.jdc.template.coroutine.channel.ViewModelChannel
import org.jdc.template.delegates.requireSavedState
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId

class IndividualEditViewModel
@ViewModelInject constructor(
    analytics: Analytics,
    private val individualRepository: IndividualRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _eventChannel: ViewModelChannel<Event> = ViewModelChannel(this)
    val eventChannel: ReceiveChannel<Event> = _eventChannel

    private val individualId: Long by requireSavedState(savedStateHandle, "individualId")
    private var individual = Individual()

    // Fields
    val firstName = mutableStateOf("")
    val firstNameError = mutableStateOf(false)
    val lastName = mutableStateOf("")
    val phone = mutableStateOf("")
    val email = mutableStateOf("")
    val birthDate = mutableStateOf<LocalDate?>(null)
    val birthDateMillis get() = birthDate.value?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    val alarmTime = mutableStateOf<LocalTime>(LocalTime.now())
    val alarmTimeMillis get() = OffsetDateTime.now().with(alarmTime.value).toInstant().toEpochMilli()

    init {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        loadIndividual()
    }

    private fun loadIndividual() = viewModelScope.launch {
        individualRepository.getIndividual(individualId)?.let {
            individual = it

            firstName.value = it.firstName
            lastName.value = it.lastName
            phone.value = it.phone
            email.value = it.email
            birthDate.value = it.birthDate
            alarmTime.value = it.alarmTime
        }
    }

    fun saveIndividual(): Boolean {
        val valid = validate()
        if (valid) GlobalScope.launch {
            individual.firstName = firstName.value
            individual.lastName = lastName.value
            individual.phone = phone.value
            individual.email = email.value
            individual.birthDate = birthDate.value
            individual.alarmTime = alarmTime.value
            individualRepository.saveIndividual(individual)
        }
        return valid
    }

    private fun validate(): Boolean {
        return firstName.value.isNotBlank().also { valid ->
            firstNameError.value = !valid   // show error state if field is not valid
        }
    }

    fun onBirthDateClicked() {
        _eventChannel.sendAsync(Event.ShowBirthDateSelection(birthDate.value ?: LocalDate.now()))
    }

    fun onAlarmTimeClicked() {
        _eventChannel.sendAsync(Event.ShowAlarmTimeSelection(alarmTime.value ?: LocalTime.now()))
    }

    enum class FieldValidationError(val errorMessageId: Int) {
        FIRST_NAME_REQUIRED(R.string.required),
    }

    sealed class Event {
        class ShowBirthDateSelection(val date: LocalDate) : Event()
        class ShowAlarmTimeSelection(val time: LocalTime) : Event()
    }
}