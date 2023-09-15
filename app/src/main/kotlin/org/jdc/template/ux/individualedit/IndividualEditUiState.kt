package org.jdc.template.ux.individualedit

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jdc.template.model.domain.type.IndividualType
import org.jdc.template.ui.compose.dialog.DialogUiState
import org.jdc.template.ui.compose.form.TextFieldData

@Suppress("LongParameterList")
class IndividualEditUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val firstNameFlow: StateFlow<TextFieldData> = MutableStateFlow(TextFieldData("")),
    val firstNameOnChange: (String) -> Unit = {},
    val lastNameFlow: StateFlow<TextFieldData> = MutableStateFlow(TextFieldData("")),
    val lastNameOnChange: (String) -> Unit = {},
    val phoneFlow: StateFlow<TextFieldData> = MutableStateFlow(TextFieldData("")),
    val phoneOnChange: (String) -> Unit = {},
    val emailFlow: StateFlow<TextFieldData> = MutableStateFlow(TextFieldData("")),
    val emailOnChange: (String) -> Unit = {},

    val birthDateFlow: StateFlow<LocalDate?> = MutableStateFlow(null),
    val birthDateClicked: () -> Unit = {},

    val alarmTimeFlow: StateFlow<LocalTime?> = MutableStateFlow(null),
    val alarmTimeClicked: () -> Unit = {},

    val individualTypeFlow: StateFlow<IndividualType> = MutableStateFlow(IndividualType.UNKNOWN),
    val individualTypeChange: (IndividualType) -> Unit = {},

    val availableFlow: StateFlow<Boolean> = MutableStateFlow(false),
    val availableOnChange: (Boolean) -> Unit = {},

    // Events
    val saveIndividual: () -> Unit = {},
)
