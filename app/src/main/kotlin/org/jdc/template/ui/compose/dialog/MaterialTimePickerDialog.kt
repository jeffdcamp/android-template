package org.jdc.template.ui.compose.dialog

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime

@Composable
fun MaterialTimePickerDialog(
    time: LocalTime,
    onDismiss: (() -> Unit)? = null,
    onTimeSelected: (LocalTime) -> Unit
){
    val context = LocalContext.current

    val timePickerDialog = TimePickerDialog(context, { _, hourOfDay, minute ->
        onTimeSelected(LocalTime.of(hourOfDay, minute))
    }, time.hour, time.minute, false)

    if (onDismiss != null) {
        timePickerDialog.setOnDismissListener { onDismiss() }
    }

    timePickerDialog.show()
}