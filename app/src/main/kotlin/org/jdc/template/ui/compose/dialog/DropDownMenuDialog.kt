package org.jdc.template.ui.compose.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.jdc.template.ui.compose.DayNightTextField

@Composable
fun DropDownMenuDialog(
    onDismissRequest: (() -> Unit) = {},
    title: @Composable () -> String? = { null },
    text: @Composable () -> String? = { null },
    initialSelectedOption: String,
    options: List<String>,
    label: @Composable () -> String? = { null },
    confirmButtonText: String = stringResource(android.R.string.ok),
    onConfirmButtonClicked: ((String) -> Unit)? = null,
    dismissButtonText: String = stringResource(android.R.string.cancel),
    onDismissButtonClicked: (() -> Unit)? = null,
    properties: DialogProperties = DialogProperties(),
    shape: Shape = DialogDefaults.DefaultCorner,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionTextFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue(initialSelectedOption, TextRange(initialSelectedOption.length))) }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Surface(
            shape = shape,
            color = backgroundColor
        ) {
            Column(
                modifier = Modifier.padding(DialogDefaults.DialogPadding)
            ) {
                // Title
                val titleText = title()
                if (titleText != null) {
                    Text(
                        text = titleText,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                val textText = text()
                if (textText != null) {
                    Text(
                        text = textText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Text Field
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    val labelText = label()
                    DayNightTextField(
                        singleLine = true,
                        readOnly = true,
                        value = selectedOptionTextFieldValue,
                        onValueChange = { },
                        label = if (labelText != null ) { { Text(labelText) } } else null,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        modifier = Modifier
                            // As of Material3 1.0.0-beta03; The `menuAnchor` modifier must be passed to the text field for correctness.
                            // (https://android-review.googlesource.com/c/platform/frameworks/support/+/2200861)
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    selectedOptionTextFieldValue = selectedOptionTextFieldValue.copy(selectionOption)
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            )
                        }
                    }
                }

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    if (onDismissButtonClicked != null) {
                        TextButton(
                            onClick = {
                                onDismissButtonClicked()
                            },
                        ) {
                            Text(dismissButtonText)
                        }
                    }
                    if (onConfirmButtonClicked != null) {
                        TextButton(
                            onClick = {
                                onConfirmButtonClicked(selectedOptionTextFieldValue.text)
                            },
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
fun DropDownMenuDialog(
    dialogUiState: DropDownMenuDialogUiState
){
    DropDownMenuDialog(
        title = dialogUiState.title,
        text = dialogUiState.text,
        initialSelectedOption = dialogUiState.initialSelectedOption,
        options = dialogUiState.options,
        onConfirmButtonClicked = { dialogUiState.onConfirm(it) },
        onDismissButtonClicked = { dialogUiState.onDismissRequest() }
    )
}

data class DropDownMenuDialogUiState(
    val title: @Composable () -> String? = { null },
    val text: @Composable () -> String? = { null },
    val initialSelectedOption: String,
    val options: List<String>,
    override val onConfirm: (String) -> Unit = {},
    override val onDismiss: () -> Unit = {},
    override val onDismissRequest: () -> Unit = {}
) : DialogUiState<String>
