package org.jdc.template.ui.compose

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.jdc.template.R
import java.util.Locale

@Composable
fun SimpleDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    title: String? = null,
    text: String? = null,
    confirmButtonText: String = stringResource(R.string.ok),
    onConfirmButtonClicked: (() -> Unit)? = null,
    dismissButtonText: String = stringResource(R.string.cancel),
    onDismissButtonClicked: (() -> Unit)? = null
) {
    if (visible) {
        require(title != null || text != null) { "Title or Text is required (if visible)" }

        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = if (title != null) {
                { Text(title) }
            } else {
                null
            },
            text = if (text != null) {
                { Text(text) }
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
                        Text(confirmButtonText.toUpperCase(Locale.getDefault()))
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
                        Text(dismissButtonText.toUpperCase(Locale.getDefault()))
                    }
                }
            }
        )
    }
}

data class SimpleDialogData(
    val visible: Boolean = false,
    val title: String? = null,
    val text: String? = null
)