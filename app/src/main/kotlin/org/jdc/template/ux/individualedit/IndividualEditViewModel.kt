package org.jdc.template.ux.individualedit

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.compose.dialog.MessageDialogData
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
import org.jdc.template.util.delegates.savedState
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class IndividualEditViewModel
@Inject constructor(
    private val application: Application,
    private val individualRepository: IndividualRepository,
    savedStateHandle: SavedStateHandle
) :  ViewModel(), ViewModelNav by ViewModelNavImpl() {
    private val individualId: String? by savedState(savedStateHandle, null)
    private var individual = Individual()

    private val _showBirthDateFlow = MutableStateFlow<LocalDate?>(null)
    val showBirthDateFlow: StateFlow<LocalDate?> = _showBirthDateFlow.asStateFlow()

    private val _showAlarmTimeFlow = MutableStateFlow<LocalTime?>(null)
    val showAlarmTimeFlow: StateFlow<LocalTime?> = _showAlarmTimeFlow.asStateFlow()

    // hold state for Compose views
    private val _firstNameFlow = MutableStateFlow("")
    val firstNameFlow: StateFlow<String> = _firstNameFlow.asStateFlow()

    private val _lastNameFlow = MutableStateFlow("")
    val lastNameFlow: StateFlow<String> = _lastNameFlow.asStateFlow()

    private val _phoneNumberFlow = MutableStateFlow("")
    val phoneNumberFlow: StateFlow<String> = _phoneNumberFlow.asStateFlow()

    private val _emailFlow = MutableStateFlow("")
    val emailFlow: StateFlow<String> = _emailFlow.asStateFlow()

    private val _birthDateFlow = MutableStateFlow<LocalDate?>(null)
    val birthDateFlow: StateFlow<LocalDate?> = _birthDateFlow.asStateFlow()

    private val _alarmTimeFlow = MutableStateFlow<LocalTime?>(null)
    val alarmTimeFlow: StateFlow<LocalTime?> = _alarmTimeFlow.asStateFlow()

    private val _messageDialogDataFlow = MutableStateFlow(MessageDialogData())
    val messageDialogDataFlow: StateFlow<MessageDialogData> = _messageDialogDataFlow.asStateFlow()

    init {
        individualId?.let { id ->
            loadIndividual(id)
        } ?: newIndividual()
    }

    private fun newIndividual() = viewModelScope.launch {
        setIndividual(Individual())
    }

    private fun loadIndividual(id: String) = viewModelScope.launch {
        individualRepository.getIndividual(id)?.let { loadedIndividual ->
            setIndividual(loadedIndividual)
        }
    }

    private fun setIndividual(individual: Individual) {
        this@IndividualEditViewModel.individual = individual

        _firstNameFlow.value = individual.firstName ?: ""
        _lastNameFlow.value = individual.lastName ?: ""
        _phoneNumberFlow.value = individual.phone ?: ""
        _emailFlow.value = individual.email ?: ""
        _birthDateFlow.value = individual.birthDate
        _alarmTimeFlow.value = individual.alarmTime
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

        popBackStack()
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
            _messageDialogDataFlow.value = MessageDialogData(true, application.getString(R.string.error), text)
            return false
        }

        return true
    }

    fun hideInfoDialog() {
        _messageDialogDataFlow.value = MessageDialogData()
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
        showBirthDate(_birthDateFlow.value ?: LocalDate.now())
    }

    fun onAlarmTimeClicked() {
        showAlarmTime(_alarmTimeFlow.value ?: LocalTime.now())
    }

    private fun showBirthDate(localDate: LocalDate) {
        _showBirthDateFlow.compareAndSet(null, localDate)
    }

    fun resetShowBirthDate(localDate: LocalDate) {
        _showBirthDateFlow.compareAndSet(localDate, null)
    }

    private fun showAlarmTime(localTime: LocalTime) {
        _showAlarmTimeFlow.compareAndSet(null, localTime)
    }

    fun resetShowAlarmTime(localTime: LocalTime) {
        _showAlarmTimeFlow.compareAndSet(localTime, null)
    }
}