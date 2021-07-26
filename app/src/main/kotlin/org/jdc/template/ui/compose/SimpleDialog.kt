package org.jdc.template.ui.compose

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.jdc.template.R
import java.util.Locale

data class SimpleDialogData(
    val visible: Boolean = false,
    val title: String? = null,
    val text: String? = null
)

@Composable
fun SimpleDialog(
    simpleDialogData: SimpleDialogData,
    onDismissRequest: () -> Unit,
    confirmButtonText: String = stringResource(R.string.ok),
    onConfirmButtonClicked: (() -> Unit)? = null,
    dismissButtonText: String = stringResource(R.string.cancel),
    onDismissButtonClicked: (() -> Unit)? = null
) {
    if (simpleDialogData.visible) {
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = { simpleDialogData.title?.let { Text(it) } },
            text = { simpleDialogData.text?.let { Text(it) } },
            confirmButton = {
                if (onConfirmButtonClicked != null) {
                    Button(
                        onClick = {
                            onConfirmButtonClicked()
                        }
                    ) {
                        Text(confirmButtonText)
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