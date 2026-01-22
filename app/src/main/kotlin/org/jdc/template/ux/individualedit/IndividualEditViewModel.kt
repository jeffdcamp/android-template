package org.jdc.template.ux.individualedit

import android.app.Application
import androidx.compose.material3.SelectableDates
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jdc.template.R
import org.jdc.template.analytics.Analytics
import org.jdc.template.shared.model.domain.Individual
import org.jdc.template.shared.model.domain.inline.Email
import org.jdc.template.shared.model.domain.inline.FirstName
import org.jdc.template.shared.model.domain.inline.LastName
import org.jdc.template.shared.model.domain.inline.Phone
import org.jdc.template.shared.model.domain.type.IndividualType
import org.jdc.template.shared.model.repository.IndividualRepository
import org.jdc.template.ui.compose.dialog.DatePickerDialogUiState
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.TimePickerDialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.ui.navigation3.Navigation3Action
import org.jdc.template.ui.navigation3.ViewModelNavigation3
import org.jdc.template.ui.navigation3.ViewModelNavigation3Impl
import kotlin.time.Clock

class IndividualEditViewModel(
    private val application: Application,
    private val individualRepository: IndividualRepository,
    analytics: Analytics,
    private val individualEditRoute: IndividualEditRoute
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?>
        field = MutableStateFlow<DialogUiState<*>?>(null)

    val uiStateFlow: StateFlow<IndividualEditUiState>
        field = MutableStateFlow<IndividualEditUiState>(IndividualEditUiState.Loading)

    private var individual = Individual()

    private val firstNameFlow = MutableStateFlow("")
    private val firstNameErrorFlow = MutableStateFlow<String?>(null)
    private val lastNameFlow = MutableStateFlow("")
    private val phoneNumberFlow = MutableStateFlow("")
    private val emailFlow = MutableStateFlow("")
    private val emailErrorFlow = MutableStateFlow<String?>(null)
    private val birthDateFlow = MutableStateFlow<LocalDate?>(null)
    private val birthDateErrorFlow = MutableStateFlow<String?>(null)
    private val alarmTimeFlow = MutableStateFlow<LocalTime?>(null)
    private val individualTypeFlow = MutableStateFlow(IndividualType.UNKNOWN)
    private val individualTypeErrorFlow = MutableStateFlow<String?>(null)
    private val availableFlow = MutableStateFlow(false)

    init {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        loadIndividual()
    }

    private fun loadIndividual() = viewModelScope.launch {
        if (individualEditRoute.individualId != null) {
            individualRepository.getIndividual(individualEditRoute.individualId)?.let { loadedIndividual ->
                setIndividual(loadedIndividual)
                individual = loadedIndividual
            }
        }

        uiStateFlow.value = IndividualEditUiState.Ready(
            IndividualEditFormFields(
                firstNameFlow = firstNameFlow,
                firstNameErrorFlow = firstNameErrorFlow,
                lastNameFlow = lastNameFlow,
                phoneNumberFlow = phoneNumberFlow,
                emailFlow = emailFlow,
                emailErrorFlow = emailErrorFlow,
                birthDateFlow = birthDateFlow,
                birthDateErrorFlow = birthDateErrorFlow,
                alarmTimeFlow = alarmTimeFlow,
                individualTypeFlow = individualTypeFlow,
                individualTypeErrorFlow = individualTypeErrorFlow,
                availableFlow = availableFlow,
            )
        )
    }

    private fun setIndividual(loadedIndividual: Individual) {
        firstNameFlow.value = loadedIndividual.firstName?.value.orEmpty()
        lastNameFlow.value = loadedIndividual.lastName?.value.orEmpty()
        phoneNumberFlow.value = loadedIndividual.phone?.value.orEmpty()
        emailFlow.value = loadedIndividual.email?.value.orEmpty()
        birthDateFlow.value = loadedIndividual.birthDate
        alarmTimeFlow.value = loadedIndividual.alarmTime
        individualTypeFlow.value = loadedIndividual.individualType
        availableFlow.value = loadedIndividual.available
    }

    fun onFirstNameChange(value: String) {
        firstNameFlow.value = value
        firstNameErrorFlow.value = null
    }

    fun onLastNameChange(value: String) {
        lastNameFlow.value = value
    }

    fun onPhoneChange(value: String) {
        phoneNumberFlow.value = value
    }

    fun onEmailChange(value: String) {
        emailFlow.value = value
        emailErrorFlow.value = null
    }

    fun onBirthDateClick() {
        showBirthDate()
        birthDateErrorFlow.value = null
    }

    fun onAlarmTimeClick() {
        showAlarmTime()
    }

    fun onIndividualTypeChange(value: IndividualType) {
        individualTypeFlow.value = value;
        individualTypeErrorFlow.value = null
    }

    fun onAvailableChange(value: Boolean) {
        availableFlow.value = value
    }

    fun onSaveClick() = viewModelScope.launch {
        if (!validateAllFields()) {
            return@launch
        }

        individualRepository.saveIndividual(individual.copy(
            firstName = valueOrNull(firstNameFlow.value)?.let { FirstName(it) },
            lastName = valueOrNull(lastNameFlow.value)?.let { LastName(it) },
            phone = valueOrNull(phoneNumberFlow.value)?.let { Phone(it) },
            email = valueOrNull(emailFlow.value)?.let { Email(it) },
            birthDate = birthDateFlow.value,
            alarmTime = alarmTimeFlow.value,
            individualType = individualTypeFlow.value,
            available = availableFlow.value,
        ))

        navigate(Navigation3Action.Pop())
    }

    private fun valueOrNull(value: String): String? {
        return value.ifBlank {
            null
        }
    }

    private fun resetErrors() {
        firstNameErrorFlow.value = null
        emailErrorFlow.value = null
        individualTypeErrorFlow.value = null
    }

    private fun validateAllFields(): Boolean {
        resetErrors()
        var allFieldsValid = true

        if (firstNameFlow.value.isBlank()) {
            firstNameErrorFlow.value = application.getString(R.string.required)
            allFieldsValid = false
        }

        if (emailFlow.value.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(emailFlow.value).matches()) {
            emailErrorFlow.value = application.getString(R.string.invalid_email)
            allFieldsValid = false
        }

        val birthDate = birthDateFlow.value
        if (birthDate != null && birthDate >= Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) {
            birthDateErrorFlow.value = application.getString(R.string.invalid_birth_date)
            return false
        }

        if (individualTypeFlow.value == IndividualType.CHILD && lastNameFlow.value.isEmpty()) {
            individualTypeErrorFlow.value = application.getString(R.string.individual_type_requires_last_name)
            return false
        }

        return allFieldsValid
    }

    private fun showBirthDate() {
        val todayMs = System.currentTimeMillis()

        dialogUiStateFlow.value = DatePickerDialogUiState(
            localDate = birthDateFlow.value,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis < todayMs
                }
            },
            onConfirm = {
                birthDateFlow.value = it
                dismissDialog(dialogUiStateFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun showAlarmTime() {
        dialogUiStateFlow.value = TimePickerDialogUiState(
            localTime = alarmTimeFlow.value ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
            onConfirm = {
                alarmTimeFlow.value = it
                dismissDialog(dialogUiStateFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) }
        )
    }

}

sealed interface IndividualEditUiState {
    data object Loading : IndividualEditUiState
    data class Ready(val formFields: IndividualEditFormFields) : IndividualEditUiState
    data object Empty : IndividualEditUiState
}

data class IndividualEditFormFields(
    val firstNameFlow: MutableStateFlow<String>,
    val firstNameErrorFlow: MutableStateFlow<String?>,
    val lastNameFlow: MutableStateFlow<String>,
    val phoneNumberFlow: MutableStateFlow<String>,
    val emailFlow: MutableStateFlow<String>,
    val emailErrorFlow: MutableStateFlow<String?>,
    val birthDateFlow: MutableStateFlow<LocalDate?>,
    val birthDateErrorFlow: MutableStateFlow<String?>,
    val alarmTimeFlow: MutableStateFlow<LocalTime?>,
    val individualTypeFlow: MutableStateFlow<IndividualType>,
    val individualTypeErrorFlow: MutableStateFlow<String?>,
    val availableFlow: MutableStateFlow<Boolean>,
)
