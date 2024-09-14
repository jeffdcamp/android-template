package org.jdc.template.ux.individualedit

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jdc.template.model.domain.type.IndividualType
import org.jdc.template.ui.compose.dialog.DialogUiState

@Suppress("LongParameterList")
class IndividualEditUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val firstNameFlow: StateFlow<String> = MutableStateFlow(""),
    val firstNameErrorFlow: StateFlow<String?> = MutableStateFlow(null),
    val firstNameOnChange: (String) -> Unit = {},
    val lastNameFlow: StateFlow<String> = MutableStateFlow(""),
    val lastNameOnChange: (String) -> Unit = {},
    val phoneFlow: StateFlow<String> = MutableStateFlow(""),
    val phoneOnChange: (String) -> Unit = {},
    val emailFlow: StateFlow<String> = MutableStateFlow(""),
    val emailErrorFlow: StateFlow<String?> = MutableStateFlow(null),
    val emailOnChange: (String) -> Unit = {},

    val birthDateFlow: StateFlow<LocalDate?> = MutableStateFlow(null),
    val birthDateErrorFlow: StateFlow<String?> = MutableStateFlow(null),
    val birthDateClick: () -> Unit = {},

    val alarmTimeFlow: StateFlow<LocalTime?> = MutableStateFlow(null),
    val alarmTimeClick: () -> Unit = {},

    val individualTypeFlow: StateFlow<IndividualType?> = MutableStateFlow(null),
    val individualTypeErrorFlow: StateFlow<String?> = MutableStateFlow(null),
    val individualTypeChange: (IndividualType) -> Unit = {},

    val availableFlow: StateFlow<Boolean> = MutableStateFlow(false),
    val availableOnChange: (Boolean) -> Unit = {},

    // Events
    val onSaveIndividualClick: () -> Unit = {},
)
