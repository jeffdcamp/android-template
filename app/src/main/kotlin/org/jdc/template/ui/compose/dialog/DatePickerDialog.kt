package org.jdc.template.ui.compose.dialog

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

@Composable
fun DatePickerDialog(
    dialogUiState: DatePickerDialogUiState
){
    val initialMs: Long? = dialogUiState.localDate?.atStartOfDay()?.atOffset(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
    val datePickerState = rememberDatePickerState(initialMs)
    val onDismissButtonClicked = dialogUiState.onDismiss

    DatePickerDialog(
        onDismissRequest = dialogUiState.onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    val ms = datePickerState.selectedDateMillis
                    if (ms != null) {
                        dialogUiState.onConfirm(Instant.ofEpochMilli(ms).atZone(ZoneId.of("UTC")).toLocalDate())
                    }
                },
            ) {
                Text(dialogUiState.confirmButtonText())
            }
        },
        dismissButton = if (onDismissButtonClicked != null) {{
                TextButton(
                    onClick = onDismissButtonClicked,
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
                        datePickerState,
                        modifier = Modifier.padding(PaddingValues(start = 24.dp, end = 12.dp, top = 16.dp)) // DatePickerTitlePadding
                    )
                }
            },
            state = datePickerState,
            dateValidator = dialogUiState.dateValidator
        )
    }
}

/**
 * DatePickerDialogUiState
 * @param title title on top of the dialog
 * @param localDate initial selected date
 * @param dateValidator a lambda that takes a date timestamp and return true if the date is a valid one for selection. Invalid dates will appear disabled in the UI.
 * @param onConfirm confirm button press with the selected LocalDate
 * @param onDismiss dismiss button press
 * @param onDismissRequest outside the dialog press
 * @param confirmButtonText text for confirm button. default to "OK"
 * @param dismissButtonText text for dismiss button. default to "Cancel"
 */
data class DatePickerDialogUiState(
    val title: (@Composable () -> Unit)? = null,
    val localDate: LocalDate? = null,
    val dateValidator: (Long) -> Boolean = { true },
    override val onConfirm: (LocalDate?) -> Unit = {},
    override val onDismiss: (() -> Unit)? = null,
    override val onDismissRequest: () -> Unit = {},
    val confirmButtonText: @Composable () -> String = { stringResource(android.R.string.ok) },
    val dismissButtonText: @Composable () -> String = { stringResource(android.R.string.cancel) },
) : DialogUiState<LocalDate>
