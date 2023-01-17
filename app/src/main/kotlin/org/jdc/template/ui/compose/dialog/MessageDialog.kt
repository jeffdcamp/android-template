package org.jdc.template.ui.compose.dialog

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.jdc.template.ui.theme.AppTheme

@Composable
fun MessageDialog(
    onDismissRequest: () -> Unit,
    title: @Composable () -> String? = { null },
    text: @Composable () -> String? = { null },
    confirmButtonText: @Composable () -> String? = { stringResource(android.R.string.ok) },
    onConfirmButtonClicked: (() -> Unit)? = null,
    dismissButtonText: @Composable () -> String? = { stringResource(android.R.string.cancel) },
    onDismissButtonClicked: (() -> Unit)? = null
) {
    val titleString = title()
    val textString = text()
    require(titleString != null || textString != null) { "Title or Text is required (if visible)" }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = if (titleString != null) {
            { Text(titleString, style = MaterialTheme.typography.headlineSmall) }
        } else {
            null
        },
        text = if (textString != null) {
            { Text(textString, style = MaterialTheme.typography.bodyMedium) }
        } else {
            null
        },
        confirmButton = {
            val confirmButtonTextString = confirmButtonText()
            if (onConfirmButtonClicked != null && confirmButtonTextString != null) {
                TextButton(
                    onClick = {
                        onConfirmButtonClicked()
                    }
                ) {
                    Text(confirmButtonTextString)
                }
            }
        },
        dismissButton = {
            val dismissButtonTextString = dismissButtonText()
            if (onDismissButtonClicked != null && dismissButtonTextString != null) {
                TextButton(
                    onClick = {
                        onDismissButtonClicked()
                    }
                ) {
                    Text(dismissButtonTextString)
                }
            }
        }
    )
}

@Composable
fun MessageDialog(
    dialogUiState: MessageDialogUiState
) {
    MessageDialog(
        onDismissRequest = dialogUiState.onDismissRequest,
        title = dialogUiState.title,
        text = dialogUiState.text,
        confirmButtonText = dialogUiState.confirmButtonText,
        onConfirmButtonClicked = if (dialogUiState.onConfirm != null) { { dialogUiState.onConfirm.invoke(Unit) } } else null,
        dismissButtonText = dialogUiState.dismissButtonText,
        onDismissButtonClicked = dialogUiState.onDismiss,
    )
}

data class MessageDialogUiState(
    val title: @Composable () -> String? = { null },
    val text: @Composable () -> String? = { null },
    val confirmButtonText: @Composable () -> String? = { stringResource(android.R.string.ok) },
    val dismissButtonText: @Composable () -> String? = { stringResource(android.R.string.cancel) },
    override val onConfirm: ((Unit) -> Unit)? = {},
    override val onDismiss: (() -> Unit)? = null,
    override val onDismissRequest: () -> Unit = {},
) : DialogUiState<Unit>

@Preview(group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Composable
private fun PreviewMessageDialog() {
    AppTheme {
        MessageDialog(
            title = { "Title" },
            text = { "This is the content that goes in the text" },
            onDismissRequest = { },
            onConfirmButtonClicked = { },
            onDismissButtonClicked = { }
        )
    }
}
