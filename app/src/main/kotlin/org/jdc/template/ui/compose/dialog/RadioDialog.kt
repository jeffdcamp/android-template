package org.jdc.template.ui.compose.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.theme.AppTheme

@Composable
fun <T> RadioDialog(
    items: RadioDialogDataItems<T>?,
    onItemSelected: (T) -> Unit,
    onDismissRequest: (() -> Unit),
    title: String? = null,
    supportingText: String? = null,
    onConfirmButtonClick: (() -> Unit)? = null,
    onDismissButtonClick: (() -> Unit)? = null,
    confirmButtonText: String? = stringResource(android.R.string.ok),
    dismissButtonText: String? = stringResource(android.R.string.cancel),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    properties: DialogProperties = DialogProperties(),
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = backgroundColor,
            tonalElevation = tonalElevation
        ) {
            Column(
                modifier = Modifier.padding(DialogDefaults.DialogPadding)
            ) {
                // Title
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                // Supporting Text
                if (supportingText != null) {
                    Text(
                        text = supportingText,
                        modifier = Modifier.padding(top = 16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Radio Items
                if (items != null) {
                    Box(Modifier.padding(top = 16.dp)) {
                        RadioDialogItems(items, onItemSelected)
                    }
                }

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    if (onDismissButtonClick != null && dismissButtonText != null) {
                        TextButton(
                            onClick = {
                                onDismissButtonClick()
                            }
                        ) {
                            Text(dismissButtonText)
                        }
                    }
                    if (onConfirmButtonClick != null && confirmButtonText != null) {
                        TextButton(
                            onClick = {
                                onConfirmButtonClick()
                            }
                        ) {
                            Text(confirmButtonText)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun <T> RadioDialogItems(radioDialogDataItems: RadioDialogDataItems<T>, onItemSelected: (T) -> Unit) {
    Column(Modifier
        .selectableGroup()
        .verticalScroll(rememberScrollState())
    ) {
        radioDialogDataItems.items.forEach { radioDialogDataItem ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .selectable(
                        selected = radioDialogDataItem.item == radioDialogDataItems.selectedItem,
                        onClick = { onItemSelected(radioDialogDataItem.item) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = radioDialogDataItem.item == radioDialogDataItems.selectedItem,
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = radioDialogDataItem.text(),
                    style = MaterialTheme.typography.bodyMedium.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun <T> RadioDialog(
    dialogUiState: RadioDialogUiState<T>
) {
    RadioDialog(
        items = dialogUiState.items,
        onItemSelected = dialogUiState.onConfirm,
        title = dialogUiState.title?.invoke(),
        supportingText = dialogUiState.supportingText?.invoke(),
        onConfirmButtonClick = null,
        onDismissRequest = dialogUiState.onDismissRequest,
        onDismissButtonClick = dialogUiState.onDismiss,
        confirmButtonText = dialogUiState.confirmButtonText(),
        dismissButtonText = dialogUiState.dismissButtonText(),
    )
}

data class RadioDialogUiState<T>(
    val items: RadioDialogDataItems<T>?,
    val title: @Composable (() -> String)? = null,
    val supportingText: @Composable (() -> String)? = null,
    val confirmButtonText: @Composable () -> String? = { stringResource(android.R.string.ok) },
    val dismissButtonText: @Composable () -> String? = { stringResource(android.R.string.cancel) },
    override val onConfirm: (T) -> Unit = {},
    override val onDismiss: (() -> Unit)? = null,
    override val onDismissRequest: () -> Unit = {},
) : DialogUiState<T>

data class RadioDialogDataItems<T>(val items: List<RadioDialogDataItem<T>>, val selectedItem: T)

data class RadioDialogDataItem<T>(val item: T, val text: @Composable () -> String)

@PreviewDefault
@Composable
private fun Preview() {
    val radioItems: RadioDialogDataItems<String> = RadioDialogDataItems(
        listOf(
            RadioDialogDataItem("id1") { "A" },
            RadioDialogDataItem("id2") { "B" },
            RadioDialogDataItem("id3") { "C" },
        ),
        "id2"
    )

    AppTheme {
        RadioDialog(
            onDismissRequest = {},
            title = "Title",
            supportingText = "Here is some supporting text",
            items = radioItems,
            onItemSelected = { },
            onDismissButtonClick = { }
        )
    }
}
