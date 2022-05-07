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
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.compose.dialog.DateDialogUiState
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.dialog.TimeDialogUiState
import org.jdc.template.ui.compose.dialog.dismissDialog
import org.jdc.template.ui.compose.dialog.showMessageDialog
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
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    private val individualId: String? by savedState(savedStateHandle, null)
    private var individual = Individual()

    // hold state for Compose views
    private val firstNameFlow = MutableStateFlow("")
    private val lastNameFlow = MutableStateFlow("")
    private val phoneNumberFlow = MutableStateFlow("")
    private val emailFlow = MutableStateFlow("")
    private val birthDateFlow = MutableStateFlow<LocalDate?>(null)
    private val alarmTimeFlow = MutableStateFlow<LocalTime?>(null)

    // Dialogs
    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)

    val uiState: IndividualEditUiState = IndividualEditUiState(
        dialogUiStateFlow = dialogUiStateFlow,

        firstNameFlow = firstNameFlow,
        firstNameOnChange = { firstNameFlow.value = it },
        lastNameFlow = lastNameFlow,
        lastNameOnChange = { lastNameFlow.value = it },
        phoneFlow = phoneNumberFlow,
        phoneOnChange = { phoneNumberFlow.value = it },
        emailFlow = emailFlow,
        emailOnChange = { emailFlow.value = it },

        birthDateFlow = birthDateFlow,
        birthDateClicked = { showBirthDate() },

        alarmTimeFlow = alarmTimeFlow,
        alarmTimeClicked = ::showAlarmTime,

        // Events
        saveIndividual = ::saveIndividual,
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

        firstNameFlow.value = individual.firstName ?: ""
        lastNameFlow.value = individual.lastName ?: ""
        phoneNumberFlow.value = individual.phone ?: ""
        emailFlow.value = individual.email ?: ""
        birthDateFlow.value = individual.birthDate
        alarmTimeFlow.value = individual.alarmTime
    }

    private fun saveIndividual() = viewModelScope.launch {
        if (!validate()) {
            return@launch
        }

        individual.firstName = valueOrNull(firstNameFlow.value)
        individual.lastName = valueOrNull(lastNameFlow.value)
        individual.phone = valueOrNull(phoneNumberFlow.value)
        individual.email = valueOrNull(emailFlow.value)
        individual.birthDate = birthDateFlow.value
        individual.alarmTime = alarmTimeFlow.value

        individualRepository.saveIndividual(individual)

        popBackStack()
    }

    private fun valueOrNull(value: String): String? {
        return value.ifBlank {
            null
        }
    }

    private fun validate(): Boolean {
        if (firstNameFlow.value.isBlank()) {
            val text = application.getString(R.string.x_required, application.getString(R.string.first_name))
            showMessageDialog(dialogUiStateFlow, title = application.getString(R.string.error), text = text)
            return false
        }

        return true
    }

    private fun showBirthDate() {
        dialogUiStateFlow.value = DateDialogUiState(
            visible = true,
            localDate = birthDateFlow.value ?: LocalDate.now(),
            onConfirm = {
                birthDateFlow.value = it
                dismissDialog(dialogUiStateFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun showAlarmTime() {
        dialogUiStateFlow.value = TimeDialogUiState(
            visible = true,
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
