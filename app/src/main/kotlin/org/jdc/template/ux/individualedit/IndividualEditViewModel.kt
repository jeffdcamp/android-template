package org.jdc.template.ux.individualedit

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.db.main.type.IndividualType
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.compose.dialog.DatePickerDialogUiState
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.TimePickerDialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.ui.compose.form.TextFieldData
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
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
    private val individualId: String? = savedStateHandle[IndividualEditRoute.Arg.INDIVIDUAL_ID]
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

    private fun loadIndividual(id: String) = viewModelScope.launch {
        individualRepository.getIndividual(id)?.let { loadedIndividual ->
            setIndividual(loadedIndividual)
        }
    }

    private fun setIndividual(individual: Individual) {

        this@IndividualEditViewModel.individual = individual

        firstNameFlow.value = TextFieldData(individual.firstName.orEmpty())
        lastNameFlow.value = TextFieldData(individual.lastName.orEmpty())
        phoneNumberFlow.value = TextFieldData(individual.phone.orEmpty())
        emailFlow.value = TextFieldData(individual.email.orEmpty())
        birthDateFlow.value = individual.birthDate
        alarmTimeFlow.value = individual.alarmTime
        individualTypeFlow.value = individual.individualType
        availableFlow.value = individual.available
    }

    private fun saveIndividual() = viewModelScope.launch {
        if (!validate()) {
            return@launch
        }

        individual.firstName = valueOrNull(firstNameFlow.value.text)
        individual.lastName = valueOrNull(lastNameFlow.value.text)
        individual.phone = valueOrNull(phoneNumberFlow.value.text)
        individual.email = valueOrNull(emailFlow.value.text)
        individual.birthDate = birthDateFlow.value
        individual.alarmTime = alarmTimeFlow.value
        individual.individualType = individualTypeFlow.value
        individual.available = availableFlow.value

        individualRepository.saveIndividual(individual)

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
        dialogUiStateFlow.value = DatePickerDialogUiState(
            localDate = birthDateFlow.value,
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
