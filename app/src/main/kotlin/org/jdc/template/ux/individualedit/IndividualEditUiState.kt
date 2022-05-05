package org.jdc.template.ux.individualedit

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.ui.compose.dialog.DateDialogData
import org.jdc.template.ui.compose.dialog.MessageDialogData
import org.jdc.template.ui.compose.dialog.TimeDialogData
import java.time.LocalDate
import java.time.LocalTime

@Suppress("LongParameterList")
class IndividualEditUiState(
    // Data
    val firstNameFlow: StateFlow<String> = MutableStateFlow(""),
    val firstNameOnChange: (String) -> Unit = {},
    val lastNameFlow: StateFlow<String> = MutableStateFlow(""),
    val lastNameOnChange: (String) -> Unit = {},
    val phoneFlow: StateFlow<String> = MutableStateFlow(""),
    val phoneOnChange: (String) -> Unit = {},
    val emailFlow: StateFlow<String> = MutableStateFlow(""),
    val emailOnChange: (String) -> Unit = {},

    // Events
    val saveIndividual: () -> Unit = {},
    val messageDialogDataFlow: StateFlow<MessageDialogData?> = MutableStateFlow(null),
    val hideMessageDialog: () -> Unit = {},

    // Dialog - Birth Date
    val birthDateFlow: StateFlow<LocalDate?> = MutableStateFlow(null),
    val birthDateDialogData: StateFlow<DateDialogData?> = MutableStateFlow(null),
    val birthDateClicked: () -> Unit = {},
    val onBirthDateSelected: (LocalDate) -> Unit = {},
    val dismissBirthDateDialog: () -> Unit = {},

    // Dialog - Alarm Time
    val alarmTimeFlow: StateFlow<LocalTime?> = MutableStateFlow(null),
    val alarmTimeDialogData: StateFlow<TimeDialogData?> = MutableStateFlow(null),
    val alarmTimeClicked: () -> Unit = {},
    val onAlarmTimeSelected: (LocalTime) -> Unit = {},
    val dismissAlarmTimeDialog: () -> Unit = {},
)
