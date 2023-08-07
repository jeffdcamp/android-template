package org.jdc.template.ux.individualedit

import android.app.Application
import androidx.compose.material3.SelectableDates
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.model.domain.Individual
import org.jdc.template.model.domain.inline.Email
import org.jdc.template.model.domain.inline.FirstName
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.model.domain.inline.LastName
import org.jdc.template.model.domain.inline.Phone
import org.jdc.template.model.domain.type.IndividualType
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.compose.dialog.DatePickerDialogUiState
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.TimePickerDialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.ui.compose.form.TextFieldData
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
import org.jdc.template.util.ext.getValueClass
import org.jdc.template.ux.individual.IndividualRoute
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class IndividualEditViewModel
@Inject constructor(
    private val application: Application,
    private val individualRepository: IndividualRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    private val individualId = savedStateHandle.getValueClass<String, IndividualId>(IndividualRoute.Arg.INDIVIDUAL_ID) { IndividualId(it) }

    private var individual = Individual()

    // hold state for Compose views
    private val firstNameFlow = MutableStateFlow(TextFieldData(""))
    private val lastNameFlow = MutableStateFlow(TextFieldData(""))
    private val phoneNumberFlow = MutableStateFlow(TextFieldData(""))
    private val emailFlow = MutableStateFlow(TextFieldData(""))
    private val birthDateFlow = MutableStateFlow<LocalDate?>(null)
    private val alarmTimeFlow = MutableStateFlow<LocalTime?>(null)
    private val individualTypeFlow = MutableStateFlow(IndividualType.UNKNOWN)
    private val availableFlow = MutableStateFlow(false)

    // Dialogs
    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)

    val uiState: IndividualEditUiState = IndividualEditUiState(
        dialogUiStateFlow = dialogUiStateFlow,

        // Data
        firstNameFlow = firstNameFlow,
        firstNameOnChange = { firstNameFlow.value = TextFieldData(it) },
        lastNameFlow = lastNameFlow,
        lastNameOnChange = { lastNameFlow.value = TextFieldData(it) },
        phoneFlow = phoneNumberFlow,
        phoneOnChange = { phoneNumberFlow.value = TextFieldData(it) },
        emailFlow = emailFlow,
        emailOnChange = { emailFlow.value = TextFieldData(it) },

        birthDateFlow = birthDateFlow,
        birthDateClicked = { showBirthDate() },

        alarmTimeFlow = alarmTimeFlow,
        alarmTimeClicked = { showAlarmTime() },

        individualTypeFlow = individualTypeFlow,
        individualTypeChange = { individualTypeFlow.value = it },

        availableFlow = availableFlow,
        availableOnChange = { availableFlow.value = it },

        // Events
        saveIndividual = { saveIndividual() }
    )

    init {
        individualId?.let { id ->
            loadIndividual(id)
        } ?: newIndividual()
    }

    private fun newIndividual() = viewModelScope.launch {
        setIndividual(Individual())
    }

    private fun loadIndividual(id: IndividualId) = viewModelScope.launch {
        individualRepository.getIndividual(id)?.let { loadedIndividual ->
            setIndividual(loadedIndividual)
        }
    }

    private fun setIndividual(individual: Individual) {
        this@IndividualEditViewModel.individual = individual

        firstNameFlow.value = TextFieldData(individual.firstName?.value.orEmpty())
        lastNameFlow.value = TextFieldData(individual.lastName?.value.orEmpty())
        phoneNumberFlow.value = TextFieldData(individual.phone?.value.orEmpty())
        emailFlow.value = TextFieldData(individual.email?.value.orEmpty())
        birthDateFlow.value = individual.birthDate
        alarmTimeFlow.value = individual.alarmTime
        individualTypeFlow.value = individual.individualType
        availableFlow.value = individual.available
    }

    private fun saveIndividual() = viewModelScope.launch {
        if (!validate()) {
            return@launch
        }

        individualRepository.saveIndividual(individual.copy(
            firstName = valueOrNull(firstNameFlow.value.text)?.let { FirstName(it) },
            lastName = valueOrNull(lastNameFlow.value.text)?.let { LastName(it) },
            phone = valueOrNull(phoneNumberFlow.value.text)?.let { Phone(it) },
            email = valueOrNull(emailFlow.value.text)?.let { Email(it) },
            birthDate = birthDateFlow.value,
            alarmTime = alarmTimeFlow.value,
            individualType = individualTypeFlow.value,
            available = availableFlow.value,
        ))

        popBackStack()
    }

    private fun valueOrNull(value: String): String? {
        return value.ifBlank {
            null
        }
    }

    private fun validate(): Boolean {
        if (firstNameFlow.value.text.isBlank()) {
            firstNameFlow.value = firstNameFlow.value.copy(isError = true, supportingText = application.getString(R.string.required))
            return false
        }

        return true
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
            localTime = alarmTimeFlow.value ?: LocalTime.now(),
            onConfirm = {
                alarmTimeFlow.value = it
                dismissDialog(dialogUiStateFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) }
        )
    }
}
