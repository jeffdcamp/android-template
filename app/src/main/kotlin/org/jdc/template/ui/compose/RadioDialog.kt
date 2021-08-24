package org.jdc.template.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.jdc.template.R
import java.util.Locale

data class RadioDialogData<T>(
    val visible: Boolean = false,
    val title: String? = null,
    val items: RadioDialogDataItems<T>? = null,
)

data class RadioDialogDataItems<T>(val items: List<RadioDialogDataItem<T>>, val selectedItem: T)

data class RadioDialogDataItem<T>(val item: T, val text: String)

@Composable
fun <T> RadioDialog(
    visible: Boolean,
    items: RadioDialogDataItems<T>?,
    onItemSelected: (T) -> Unit,
    title: String? = null,
    onConfirmButtonClicked: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
    onDismissButtonClicked: (() -> Unit)? = null,
    confirmButtonText: String = stringResource(R.string.ok),
    dismissButtonText: String = stringResource(R.string.cancel),
) {
    if (visible) {
        AlertDialog(
            title = if (title != null) {
                { Text(title) }
            } else {
                null
            },
            text = {
                if (items != null) {
                    RadioDialogItems(items, onItemSelected)
                }
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
            onDismissRequest = { if (onDismissRequest != null) onDismissRequest() },
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

@Composable
private fun <T> RadioDialogItems(radioDialogDataItems: RadioDialogDataItems<T>, onItemSelected: (T) -> Unit) {
    Column(Modifier.selectableGroup()) {
        radioDialogDataItems.items.forEach { radioDialogDataItem ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .selectable(
                        selected = (radioDialogDataItem.item == radioDialogDataItems.selectedItem),
                        onClick = { onItemSelected(radioDialogDataItem.item) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (radioDialogDataItem.item == radioDialogDataItems.selectedItem),
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = radioDialogDataItem.text,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}