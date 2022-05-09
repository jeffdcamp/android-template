package org.jdc.template.ui.compose.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import java.util.Locale

@Composable
fun MessageDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    text: String? = null,
    confirmButtonText: String = stringResource(android.R.string.ok),
    onConfirmButtonClicked: (() -> Unit)? = null,
    dismissButtonText: String = stringResource(android.R.string.cancel),
    onDismissButtonClicked: (() -> Unit)? = null,
    textButtonColor: Color = MaterialTheme.colors.primary
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
                    Text(confirmButtonText.uppercase(Locale.getDefault()), color = textButtonColor)
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
                    Text(dismissButtonText.uppercase(Locale.getDefault()), color = textButtonColor)
                }
            }
        }
    )
}

@Composable
fun MessageDialog(
    dialogUiState: MessageDialogUiState
){
    MessageDialog(
        onDismissRequest = { dialogUiState.onDismissRequest() },
        title = dialogUiState.title,
        text = dialogUiState.text,
        confirmButtonText = dialogUiState.confirmButtonText ?: stringResource(android.R.string.ok),
        onConfirmButtonClicked = { dialogUiState.onConfirm(Unit) },
        dismissButtonText = dialogUiState.dismissButtonText ?: stringResource(android.R.string.cancel),
        onDismissButtonClicked = if (dialogUiState.onDismiss != null) { { dialogUiState.onDismiss.invoke() } } else null
    )
}

data class MessageDialogUiState(
    val title: String? = null,
    val text: String? = null,
    val confirmButtonText: String? = null,
    val dismissButtonText: String? = null,
    override val onConfirm: (Unit) -> Unit = {},
    override val onDismiss: (() -> Unit)? = null,
    override val onDismissRequest: () -> Unit = {},
) : DialogUiState<Unit>
