package org.jdc.template.ux.individualedit

import androidx.databinding.ObservableField
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
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()
    val phone = ObservableField<String>()
    val email = ObservableField<String>()
    val birthDate = ObservableField<LocalDate?>()
    val alarmTime = ObservableField<LocalTime>()

    init {
        loadIndividual()
    }

    private fun loadIndividual() = viewModelScope.launch {
        individualRepository.getIndividual(individualId)?.let {
            individual = it

            firstName.set(it.firstName)
            lastName.set(it.lastName)
            phone.set(it.phone)
            email.set(it.email)
            birthDate.set(it.birthDate)
            alarmTime.set(it.alarmTime)
        }
    }

    fun saveIndividual() = viewModelScope.launch {
        if (!validate()) {
            return@launch
        }

        individual.firstName = firstName.get() ?: return@launch
        individual.lastName = lastName.get() ?: ""
        individual.phone = phone.get() ?: ""
        individual.email = email.get() ?: ""
        individual.birthDate = birthDate.get()
        individual.alarmTime = alarmTime.get() ?: LocalTime.now()

        individualRepository.saveIndividual(individual)

        sendEvent(Event.IndividualSaved)
    }

    private fun validate(): Boolean {
        if (firstName.get().isNullOrBlank()) {
            sendEvent(Event.ValidationSaveError(FieldValidationError.FIRST_NAME_REQUIRED))
            return false
        }

        return true
    }

    fun onBirthDateClicked() {
        sendEvent(Event.ShowBirthDateSelection(birthDate.get() ?: LocalDate.now()))
    }

    fun onAlarmTimeClicked() {
        sendEvent(Event.ShowAlarmTimeSelection(alarmTime.get() ?: LocalTime.now()))
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