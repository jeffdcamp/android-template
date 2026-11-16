package org.jdc.template.ux.individualedit

import android.app.Application
import androidx.compose.material3.SelectableDates
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jdc.template.R
import org.jdc.template.analytics.AnalyticEvent
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
import org.jdc.template.ui.navigation.Navigation3Action
import org.jdc.template.ui.navigation.ViewModelNavigation3
import org.jdc.template.ui.navigation.ViewModelNavigation3Impl
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

    // original individual (keeps fields that are not on the form, such as id and householdId, when saving)
    private var individual = Individual()

    init {
        analytics.logEvent(AnalyticEvent(Analytics.EVENT_EDIT_INDIVIDUAL))
        loadIndividual()
    }

    private fun loadIndividual() = viewModelScope.launch {
        individualEditRoute.individualId
            ?.let { individualRepository.getIndividual(it) }
            ?.let { individual = it }

        uiStateFlow.value = IndividualEditUiState.Ready(individual.toFormFields())
    }

    private fun Individual.toFormFields(): IndividualEditFormFields = IndividualEditFormFields(
        firstName = firstName?.value.orEmpty(),
        lastName = lastName?.value.orEmpty(),
        phoneNumber = phone?.value.orEmpty(),
        email = email?.value.orEmpty(),
        birthDate = birthDate,
        alarmTime = alarmTime,
        individualType = individualType,
        available = available,
    )

    /**
     * Current form fields, or null if the form has not loaded yet
     */
    private fun currentFormFields(): IndividualEditFormFields? {
        return (uiStateFlow.value as? IndividualEditUiState.Ready)?.formFields
    }

    /**
     * Updates the form fields synchronously (must stay synchronous so text fields do not lose characters or jump the cursor)
     */
    private fun updateFormFields(block: (IndividualEditFormFields) -> IndividualEditFormFields) {
        uiStateFlow.update { uiState ->
            if (uiState is IndividualEditUiState.Ready) uiState.copy(formFields = block(uiState.formFields)) else uiState
        }
    }

    fun onFirstNameChange(value: String) {
        updateFormFields { it.copy(firstName = value, firstNameError = null) }
    }

    fun onLastNameChange(value: String) {
        updateFormFields { it.copy(lastName = value) }
    }

    fun onPhoneChange(value: String) {
        updateFormFields { it.copy(phoneNumber = value) }
    }

    fun onEmailChange(value: String) {
        updateFormFields { it.copy(email = value, emailError = null) }
    }

    fun onBirthDateClick() {
        showBirthDate()
        updateFormFields { it.copy(birthDateError = null) }
    }

    fun onAlarmTimeClick() {
        showAlarmTime()
    }

    fun onIndividualTypeChange(value: IndividualType) {
        updateFormFields { it.copy(individualType = value, individualTypeError = null) }
    }

    fun onAvailableChange(value: Boolean) {
        updateFormFields { it.copy(available = value) }
    }

    fun onSaveClick() = viewModelScope.launch {
        if (!validateAllFields()) {
            return@launch
        }

        val formFields = currentFormFields() ?: return@launch

        individualRepository.saveIndividual(individual.copy(
            firstName = valueOrNull(formFields.firstName)?.let { FirstName(it) },
            lastName = valueOrNull(formFields.lastName)?.let { LastName(it) },
            phone = valueOrNull(formFields.phoneNumber)?.let { Phone(it) },
            email = valueOrNull(formFields.email)?.let { Email(it) },
            birthDate = formFields.birthDate,
            alarmTime = formFields.alarmTime,
            individualType = formFields.individualType,
            available = formFields.available,
        ))

        navigate(Navigation3Action.Pop())
    }

    private fun valueOrNull(value: String): String? {
        return value.ifBlank {
            null
        }
    }

    /**
     * Sets (or clears) the error on every field
     *
     * @return true if all fields are valid
     */
    private fun validateAllFields(): Boolean {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        updateFormFields { formFields ->
            formFields.copy(
                firstNameError = application.getString(R.string.required).takeIf { formFields.firstName.isBlank() },
                emailError = application.getString(R.string.invalid_email).takeIf {
                    formFields.email.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(formFields.email).matches()
                },
                birthDateError = application.getString(R.string.invalid_birth_date).takeIf {
                    formFields.birthDate != null && formFields.birthDate >= today
                },
                individualTypeError = application.getString(R.string.individual_type_requires_last_name).takeIf {
                    formFields.individualType == IndividualType.CHILD && formFields.lastName.isEmpty()
                },
            )
        }

        val formFields = currentFormFields() ?: return false
        return listOfNotNull(
            formFields.firstNameError,
            formFields.emailError,
            formFields.birthDateError,
            formFields.individualTypeError,
        ).isEmpty()
    }

    private fun showBirthDate() {
        val todayMs = System.currentTimeMillis()

        dialogUiStateFlow.value = DatePickerDialogUiState(
            localDate = currentFormFields()?.birthDate,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis < todayMs
                }
            },
            onConfirm = { birthDate ->
                updateFormFields { it.copy(birthDate = birthDate) }
                dismissDialog(dialogUiStateFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun showAlarmTime() {
        dialogUiStateFlow.value = TimePickerDialogUiState(
            localTime = currentFormFields()?.alarmTime ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
            onConfirm = { alarmTime ->
                updateFormFields { it.copy(alarmTime = alarmTime) }
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
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val emailError: String? = null,
    val birthDate: LocalDate? = null,
    val birthDateError: String? = null,
    val alarmTime: LocalTime? = null,
    val individualType: IndividualType = IndividualType.UNKNOWN,
    val individualTypeError: String? = null,
    val available: Boolean = false,
)
