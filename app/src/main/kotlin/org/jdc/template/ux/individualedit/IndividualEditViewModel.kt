package org.jdc.template.ux.individualedit

import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import com.squareup.inject.assisted.Assisted
import com.vikingsen.inject.viewmodel.ViewModelInject
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.livedata.EmptySingleLiveEvent
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.viewmodel.BaseViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class IndividualEditViewModel
@ViewModelInject constructor(
        private val individualRepository: IndividualRepository,
        @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val individualId = requireNotNull(savedStateHandle.get<Long>("individualId")) { "individualId cannot be null" }
    private var individual = Individual()

    // Fields
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()
    val phone = ObservableField<String>()
    val email = ObservableField<String>()
    val birthDate = ObservableField<LocalDate?>()
    val alarmTime = ObservableField<LocalTime>()

    // Events
    val onIndividualSavedEvent = EmptySingleLiveEvent()
    val onValidationSaveErrorEvent = SingleLiveEvent<FieldValidationError>()
    val onShowBirthDateSelectionEvent = SingleLiveEvent<LocalDate>()
    val onShowAlarmTimeSelectionEvent = SingleLiveEvent<LocalTime>()

    init {
        loadIndividual()
    }

    private fun loadIndividual() = launch {
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

    fun saveIndividual() = launch {
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

        onIndividualSavedEvent.postCall()
    }

    private fun validate(): Boolean {
        if (firstName.get().isNullOrBlank()) {
            onValidationSaveErrorEvent.postValue(FieldValidationError.FIRST_NAME_REQUIRED)
            return false
        }

        return true
    }

    fun onBirthDateClicked() {
        onShowBirthDateSelectionEvent.postValue(birthDate.get() ?: LocalDate.now())
    }

    fun onAlarmTimeClicked() {
        onShowAlarmTimeSelectionEvent.postValue(alarmTime.get() ?: LocalTime.now())
    }

    enum class FieldValidationError(val errorMessageId: Int) {
        FIRST_NAME_REQUIRED(R.string.required),
    }
}