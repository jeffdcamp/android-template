package org.jdc.template.ui.compose.dialog

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
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
                { DatePickerDefaults.DatePickerTitle(datePickerState) }
            },
            state = datePickerState,
        )
    }
}

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