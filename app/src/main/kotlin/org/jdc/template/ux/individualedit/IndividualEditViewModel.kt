package org.jdc.template.ux.individualedit

import androidx.compose.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.delegates.requireSavedState
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.viewmodel.BaseViewModel
import java.time.LocalDate
import java.time.LocalTime

class IndividualEditViewModel
@ViewModelInject constructor(
    private val individualRepository: IndividualRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<IndividualEditViewModel.Event>() {

    private val individualId: Long by requireSavedState(savedStateHandle, "individualId")
    private var individual = Individual()

    // Fields
    val firstName = mutableStateOf("")
    val firstNameError = mutableStateOf(false)
    val lastName = mutableStateOf("")
    val phone = mutableStateOf("")
    val email = mutableStateOf("")
    val birthDate = mutableStateOf<LocalDate?>(null)
    val birthDateFormatted = mutableStateOf("")
    val alarmTime = mutableStateOf<LocalTime>(LocalTime.now())
    val alarmTimeFormatted = mutableStateOf("")

    init {
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

    fun saveIndividual() = viewModelScope.launch {
        if (!validate()) {
            return@launch
        }

        individual.firstName = firstName.value
        individual.lastName = lastName.value
        individual.phone = phone.value
        individual.email = email.value
        individual.birthDate = birthDate.value
        individual.alarmTime = alarmTime.value

        individualRepository.saveIndividual(individual)

        sendEvent(Event.IndividualSaved)
    }

    private fun validate(): Boolean {
        if (firstName.value.isBlank()) {
            firstNameError.value = true
//            sendEvent(Event.ValidationSaveError(FieldValidationError.FIRST_NAME_REQUIRED))
            return false
        }
        firstNameError.value = false

        return true
    }

//    fun onBirthDateClicked() {
//        sendEvent(Event.ShowBirthDateSelection(birthDate.value ?: LocalDate.now()))
//    }

//    fun onAlarmTimeClicked() {
//        sendEvent(Event.ShowAlarmTimeSelection(alarmTime.value))
//    }

    enum class FieldValidationError(val errorMessageId: Int) {
        FIRST_NAME_REQUIRED(R.string.required),
    }

    sealed class Event {
        object IndividualSaved : Event()
//        class ShowBirthDateSelection(val date: LocalDate) : Event()
//        class ShowAlarmTimeSelection(val time: LocalTime) : Event()
//        class ValidationSaveError(val error: FieldValidationError) : Event()
    }
}