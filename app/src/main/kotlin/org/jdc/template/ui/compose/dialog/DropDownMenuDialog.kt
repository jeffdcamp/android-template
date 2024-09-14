package org.jdc.template.ui.compose.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
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

@Composable
fun <T> DropDownMenuDialog(
    onDismissRequest: (() -> Unit) = {},
    title: String? = null,
    supportingText: String? = null,
    initialSelectedOption: T,
    options: List<T>,
    optionToText: @Composable (T) -> String,
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

                if (supportingText != null) {
                    Text(
                        text = supportingText,
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
                        value = optionToText(selectedOptionTextFieldValue),
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
                                text = { Text(optionToText(selectionOption)) },
                                onClick = {
                                    selectedOptionTextFieldValue = selectionOption
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
                    if (onDismissButtonClick != null) {
                        TextButton(
                            onClick = {
                                onDismissButtonClick()
                            },
                        ) {
                            Text(dismissButtonText)
                        }
                    }
                    if (onConfirmButtonClick != null) {
                        TextButton(
                            onClick = {
                                onConfirmButtonClick(selectedOptionTextFieldValue)
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
fun <T> DropDownMenuDialog(
    dialogUiState: DropDownMenuDialogUiState<T>
){
    DropDownMenuDialog(
        title = dialogUiState.title?.invoke(),
        supportingText = dialogUiState.supportingText?.invoke(),
        initialSelectedOption = dialogUiState.initialSelectedOption,
        options = dialogUiState.options,
        optionToText = dialogUiState.optionToText,
        onConfirmButtonClick = { dialogUiState.onConfirm(it) },
        onDismissButtonClick = { dialogUiState.onDismissRequest() }
    )
}

data class DropDownMenuDialogUiState<T>(
    val title: @Composable (() -> String)? = null,
    val supportingText: @Composable (() -> String)? = null,
    val initialSelectedOption: T,
    val options: List<T>,
    val optionToText: @Composable (T) -> String,
    override val onConfirm: (T) -> Unit = {},
    override val onDismiss: () -> Unit = {},
    override val onDismissRequest: () -> Unit = {}
) : DialogUiState<T>

@PreviewDefault
@Composable
private fun Preview() {
    AppTheme {
        DropDownMenuDialog(
            onDismissRequest = {},
            title = "Title",
            supportingText = "Here is some supporting text",
            initialSelectedOption = "Initial",
            optionToText = { "" },
            options = listOf("A", "B", "C"),
            onConfirmButtonClick = { },
            onDismissButtonClick = { }
        )
    }
}
