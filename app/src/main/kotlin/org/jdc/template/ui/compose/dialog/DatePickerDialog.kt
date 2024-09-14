package org.jdc.template.ui.compose.dialog

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

@Composable
fun DatePickerDialog(
    dialogUiState: DatePickerDialogUiState
){
    val initialMs: Long? = dialogUiState.localDate?.atStartOfDayIn(TimeZone.UTC)?.toEpochMilliseconds()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialMs, selectableDates = dialogUiState.selectableDates)
    val onDismissButtonClick = dialogUiState.onDismiss

    DatePickerDialog(
        onDismissRequest = dialogUiState.onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    val ms = datePickerState.selectedDateMillis
                    if (ms != null) {
                        dialogUiState.onConfirm(Instant.fromEpochMilliseconds(ms).toLocalDateTime(TimeZone.UTC).date)
                    }
                },
            ) {
                Text(dialogUiState.confirmButtonText())
            }
        },
        dismissButton = if (onDismissButtonClick != null) {{
            TextButton(
                onClick = onDismissButtonClick,
            ) {
                Text(dialogUiState.dismissButtonText())
            }
        }
        } else null,
    ) {
        DatePicker(
            title = if (dialogUiState.title != null) {
                dialogUiState.title
            } else {
                {
                    DatePickerDefaults.DatePickerTitle(
                        displayMode = datePickerState.displayMode,
                        modifier = Modifier.padding(PaddingValues(start = 24.dp, end = 12.dp, top = 16.dp))
                    )
                }
            },
            state = datePickerState
        )
    }
}

/**
 * DatePickerDialogUiState
 * @property title title on top of the dialog
 * @property localDate initial selected date
 * @property selectableDates a SelectableDates that is consulted to check if a date is allowed. In case a date is not allowed to be selected, it will appear disabled in the UI.
 * @property onConfirm confirm button press with the selected LocalDate
 * @property onDismiss dismiss button press
 * @property onDismissRequest outside the dialog press
 * @property confirmButtonText text for confirm button. default to "OK"
 * @property dismissButtonText text for dismiss button. default to "Cancel"
 */
data class DatePickerDialogUiState(
    val title: (@Composable () -> Unit)? = null,
    val localDate: LocalDate? = null,
    val selectableDates: SelectableDates = object : SelectableDates {},
    override val onConfirm: (LocalDate?) -> Unit = {},
    override val onDismiss: (() -> Unit)? = null,
    override val onDismissRequest: () -> Unit = {},
    val confirmButtonText: @Composable () -> String = { stringResource(android.R.string.ok) },
    val dismissButtonText: @Composable () -> String = { stringResource(android.R.string.cancel) },
) : DialogUiState<LocalDate>
