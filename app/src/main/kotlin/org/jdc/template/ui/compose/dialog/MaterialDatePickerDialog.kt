package org.jdc.template.ui.compose.dialog

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate

@Composable
fun MaterialDatePickerDialog(
    date: LocalDate,
    onDismiss: (() -> Unit)? = null,
    onDateSelected: (LocalDate) -> Unit
){
    val context = LocalContext.current

    val datePickerDialog = DatePickerDialog(context, { _, year, monthOfYear, dayOfMonth ->
        onDateSelected(LocalDate.of(year, monthOfYear + 1, dayOfMonth)) // + 1 because core Java Date is 0 based
    }, date.year, date.monthValue - 1, date.dayOfMonth) // - 1 because core Java Date is 0 based

    if (onDismiss != null) {
        datePickerDialog.setOnDismissListener { onDismiss() }
    }

    datePickerDialog.show()
}

data class DateDialogData(
    override val visible: Boolean = false,
    val localDate: LocalDate? = null,
) : DialogData