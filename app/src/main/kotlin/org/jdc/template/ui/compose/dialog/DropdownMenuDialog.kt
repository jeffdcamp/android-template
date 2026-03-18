package org.jdc.template.ui.compose.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.jdc.template.ui.compose.DayNightTextField
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> DropdownMenuDialog(
    onDismissRequest: (() -> Unit) = {},
    title: String? = null,
    text: String? = null,
    initialSelectedOption: T,
    options: List<T>,
    optionToText: @Composable (T) -> String,
    optionToSupportingText: @Composable (T) -> String? = { null },
    label: @Composable () -> String? = { null },
    confirmButtonText: String = stringResource(android.R.string.ok),
    onConfirmButtonClick: ((T) -> Unit)? = null,
    dismissButtonText: String = stringResource(android.R.string.cancel),
    onDismissButtonClick: (() -> Unit)? = null,
    properties: DialogProperties = DialogProperties(),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionTextFieldValue: T by remember { mutableStateOf(initialSelectedOption) }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
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

                // Dialog text
                if (text != null) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Text Field
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 16.dp, end = 16.dp)
                ) {
                    val labelText = label()
                    DayNightTextField(
                        value = optionToText(selectedOptionTextFieldValue),
                        onValueChange = { },
                        modifier = Modifier.fillMaxWidth().menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                        readOnly = true,
                        label = if (labelText != null) { { Text(labelText) } } else null,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        supportingText = optionToSupportingText(selectedOptionTextFieldValue)?.let { supportingText -> { Text(supportingText) } },
                        singleLine = true
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEachIndexed { index, selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOptionTextFieldValue = selectionOption
                                    expanded = false
                                },
                                text = { Text(optionToText(selectionOption)) },
                                shape = MenuDefaults.itemShape(index = index, count = options.size).shape,
                                supportingText = optionToSupportingText(selectionOption)?.let { { Text(it) } },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
                ) {
                    if (onDismissButtonClick != null) {
                        TextButton(
                            onClick = { onDismissButtonClick() }
                        ) {
                            Text(dismissButtonText)
                        }
                    }
                    if (onConfirmButtonClick != null) {
                        TextButton(
                            onClick = { onConfirmButtonClick(selectedOptionTextFieldValue) }
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
fun <T> DropdownMenuDialog(
    dialogUiState: DropdownMenuDialogUiState<T>
){
    DropdownMenuDialog(
        onDismissRequest = dialogUiState.onDismissRequest,
        title = dialogUiState.title?.invoke(),
        text = dialogUiState.text?.invoke(),
        initialSelectedOption = dialogUiState.initialSelectedOption,
        options = dialogUiState.options,
        optionToText = dialogUiState.optionToText,
        optionToSupportingText = dialogUiState.optionToSupportingText,
        properties = dialogUiState.properties,
        onConfirmButtonClick = dialogUiState.onConfirm,
        onDismissButtonClick = dialogUiState.onDismiss
    )
}

data class DropdownMenuDialogUiState<T>(
    val title: @Composable (() -> String)? = null,
    val text: @Composable (() -> String)? = null,
    val initialSelectedOption: T,
    val options: List<T>,
    val optionToText: @Composable (T) -> String,
    val optionToSupportingText: @Composable (T) -> String? = { null },
    val properties: DialogProperties = DialogProperties(),
    override val onConfirm: (T) -> Unit = {},
    override val onDismiss: () -> Unit = {},
    override val onDismissRequest: () -> Unit = {}
) : DialogUiState<T>

@PreviewDefault
@Composable
private fun Preview() {
    AppTheme {
        DropdownMenuDialog(
            onDismissRequest = {},
            title = "Title",
            text = "Here is some text for the dialog that gives more information about the dropdown menu below.",
            initialSelectedOption = "Initial",
            optionToText = { it },
            optionToSupportingText = { "Supporting text for $it" },
            options = listOf("A", "B", "C"),
            onConfirmButtonClick = { },
            onDismissButtonClick = { }
        )
    }
}
