package org.jdc.template.ui.compose.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import org.jdc.template.ui.compose.PreviewDefault

@Composable
fun MessageDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    text: String? = null,
    icon: @Composable (() -> Unit)? = null,
    confirmButtonText: @Composable () -> String? = { stringResource(android.R.string.ok) },
    onConfirmButtonClick: (() -> Unit)? = null,
    dismissButtonText: @Composable () -> String? = { stringResource(android.R.string.cancel) },
    onDismissButtonClick: (() -> Unit)? = null,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation
) {
    require(title != null || text != null) { "Title or Text is required (if visible)" }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = if (title != null) {
            { Text(title, style = MaterialTheme.typography.headlineSmall) }
        } else {
            null
        },
        text = if (text != null) {
            {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Text(text, style = MaterialTheme.typography.bodyMedium)
                }
            }
        } else {
            null
        },
        icon = icon,
        confirmButton = {
            val confirmButtonTextString = confirmButtonText()
            if (onConfirmButtonClick != null && confirmButtonTextString != null) {
                TextButton(
                    onClick = {
                        onConfirmButtonClick()
                    }
                ) {
                    Text(confirmButtonTextString)
                }
            }
        },
        dismissButton = {
            val dismissButtonTextString = dismissButtonText()
            if (onDismissButtonClick != null && dismissButtonTextString != null) {
                TextButton(
                    onClick = {
                        onDismissButtonClick()
                    }
                ) {
                    Text(dismissButtonTextString)
                }
            }
        },
        tonalElevation = tonalElevation
    )
}

@Composable
fun MessageDialog(
    dialogUiState: MessageDialogUiState
) {
    MessageDialog(
        onDismissRequest = dialogUiState.onDismissRequest,
        title = dialogUiState.title?.invoke(),
        text = dialogUiState.text?.invoke(),
        icon = dialogUiState.icon,
        confirmButtonText = dialogUiState.confirmButtonText,
        onConfirmButtonClick = if (dialogUiState.onConfirm != null) { { dialogUiState.onConfirm.invoke(Unit) } } else null,
        dismissButtonText = dialogUiState.dismissButtonText,
        onDismissButtonClick = dialogUiState.onDismiss,
    )
}

data class MessageDialogUiState(
    val title: @Composable (() -> String)? = null,
    val text: @Composable (() -> String)? = null,
    val icon: @Composable (() -> Unit)? = null,
    val confirmButtonText: @Composable () -> String? = { stringResource(android.R.string.ok) },
    val dismissButtonText: @Composable () -> String? = { stringResource(android.R.string.cancel) },
    override val onConfirm: ((Unit) -> Unit)? = {},
    override val onDismiss: (() -> Unit)? = null,
    override val onDismissRequest: () -> Unit = {},
) : DialogUiState<Unit>

@PreviewDefault
@Composable
private fun PreviewMessageDialog() {
    MaterialTheme {
        MessageDialog(
            title = "Title",
            text = "This is the content that goes in the text",
            onDismissRequest = { },
            onConfirmButtonClick = { },
            onDismissButtonClick = { }
        )
    }
}

@PreviewDefault
@Composable
private fun PreviewMessageDialogWithIcon() {
    MaterialTheme {
        MessageDialog(
            title = "Title",
            text = "This is the content that goes in the text",
            icon = { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null) },
            onDismissRequest = { },
            onConfirmButtonClick = { },
            onDismissButtonClick = { }
        )
    }
}
