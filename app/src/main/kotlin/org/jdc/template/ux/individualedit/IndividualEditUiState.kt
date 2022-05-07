package org.jdc.template.ux.individualedit

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.ui.compose.dialog.DialogUiState
import java.time.LocalDate
import java.time.LocalTime

@Suppress("LongParameterList")
class IndividualEditUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val firstNameFlow: StateFlow<String> = MutableStateFlow(""),
    val firstNameOnChange: (String) -> Unit = {},
    val lastNameFlow: StateFlow<String> = MutableStateFlow(""),
    val lastNameOnChange: (String) -> Unit = {},
    val phoneFlow: StateFlow<String> = MutableStateFlow(""),
    val phoneOnChange: (String) -> Unit = {},
    val emailFlow: StateFlow<String> = MutableStateFlow(""),
    val emailOnChange: (String) -> Unit = {},

    val birthDateFlow: StateFlow<LocalDate?> = MutableStateFlow(null),
    val birthDateClicked: () -> Unit = {},

    val alarmTimeFlow: StateFlow<LocalTime?> = MutableStateFlow(null),
    val alarmTimeClicked: () -> Unit = {},

    // Events
    val saveIndividual: () -> Unit = {},
)
