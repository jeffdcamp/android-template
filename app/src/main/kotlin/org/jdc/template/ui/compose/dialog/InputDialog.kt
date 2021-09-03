package org.jdc.template.ui.compose.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.jdc.template.R
import org.jdc.template.ui.compose.DayNightTextField
import java.util.Locale

@Composable
fun InputDialog(
    onDismissRequest: (() -> Unit) = {},
    title: String? = null,
    textFieldLabel: String? = null,
    initialTextFieldText: String? = null,
    confirmButtonText: String = stringResource(R.string.ok),
    onConfirmButtonClicked: ((String) -> Unit)? = null,
    dismissButtonText: String = stringResource(R.string.cancel),
    onDismissButtonClicked: (() -> Unit)? = null,
    minLength: Int = -1,
    maxLength: Int = -1,
    properties: DialogProperties = DialogProperties(),
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface
) {
    var textValue: String by rememberSaveable {
        mutableStateOf(initialTextFieldText ?: "")
    }

    // Test: request focus on TextField
//    val focusRequester = remember { FocusRequester() }
//    val keyboardController = LocalSoftwareKeyboardController.current // keyboardController?.show()

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Surface(
            shape = shape,
            color = backgroundColor
        ) {
            Column {
                // Title
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )
                }

                // Text Field
                DayNightTextField(
                    value = textValue,
                    onValueChange = {
                        textValue = if (maxLength > 0) {
                            it.take(maxLength)
                        } else {
                            it
                        }
                    },
                    label = if (textFieldLabel != null) {
                        { Text(textFieldLabel) }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
//                        .focusRequester(focusRequester)
                )


                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)

                ) {
                    if (onDismissButtonClicked != null) {
                        TextButton(
                            onClick = {
                                onDismissButtonClicked()
                            }
                        ) {
                            Text(dismissButtonText.toUpperCase(Locale.getDefault()))
                        }
                    }
                    if (onConfirmButtonClicked != null) {
                        TextButton(
                            enabled = minLength == -1 || textValue.length >= minLength,
                            onClick = {
                                onConfirmButtonClicked(textValue)
                            }
                        ) {
                            Text(confirmButtonText.toUpperCase(Locale.getDefault()))
                        }
                    }
                }

                // Delay requesting focus on TextField so it does not happen during composition
//                DisposableEffect(Unit) {
//                    focusRequester.requestFocus()
//                    onDispose { }
//                }
            }
        }
    }
}

data class InputDialogData(
    val visible: Boolean = false,
    val title: String? = null,
    val initialTextFieldText: String? = null
)


