package org.jdc.template.ui.compose.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.jdc.template.R
import java.util.Locale

@Composable
fun MessageDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    text: String? = null,
    confirmButtonText: String = stringResource(R.string.ok),
    onConfirmButtonClicked: (() -> Unit)? = null,
    dismissButtonText: String = stringResource(R.string.cancel),
    onDismissButtonClicked: (() -> Unit)? = null
) {
    require(title != null || text != null) { "Title or Text is required (if visible)" }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = if (title != null) {
            { Text(title, style = MaterialTheme.typography.h6) }
        } else {
            null
        },
        text = if (text != null) {
            { Text(text, style = MaterialTheme.typography.body1) }
        } else {
            null
        },
        confirmButton = {
            if (onConfirmButtonClicked != null) {
                TextButton(
                    onClick = {
                        onConfirmButtonClicked()
                    }
                ) {
                    Text(confirmButtonText.uppercase(Locale.getDefault()))
                }
            }
        },
        dismissButton = {
            if (onDismissButtonClicked != null) {
                TextButton(
                    onClick = {
                        onDismissButtonClicked()
                    }
                ) {
                    Text(dismissButtonText.uppercase(Locale.getDefault()))
                }
            }
        }
    )
}

data class MessageDialogData(
    override val visible: Boolean = false,
    val title: String? = null,
    val text: String? = null
) : DialogData