package org.jdc.template.ui.compose.dialog

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay
import org.jdc.template.R
import org.jdc.template.ui.compose.DayNightTextField
import org.jdc.template.ui.theme.AppTheme

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
    shape: Shape = DialogDefaults.DefaultCorner,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textButtonColor: Color = MaterialTheme.colorScheme.primary, // This is specifically for handling theming in this app. May not want in Commons.
) {
    var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue(initialTextFieldText ?: "", TextRange(initialTextFieldText?.length ?: 0))) }

    // Test: request focus on TextField
    val focusRequester = remember { FocusRequester() }

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
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                val keyboardController = LocalSoftwareKeyboardController.current

                // Text Field
                DayNightTextField(
                    value = textFieldValue,
                    onValueChange = { newTextFieldValue ->
                        val newText = if (maxLength > 0) {
                            newTextFieldValue.text.take(maxLength)
                        } else {
                            newTextFieldValue.text
                        }

                        textFieldValue = newTextFieldValue.copy(newText, selection = TextRange(newText.length))
                    },
                    label = if (textFieldLabel != null) {
                        { Text(textFieldLabel) }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )

                LaunchedEffect(Unit) {
                    delay(200) // Need to have a small delay or the keyboard does not appear on the focus: https://issuetracker.google.com/issues/204502668
                    focusRequester.requestFocus()
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
                            }
                        ) {
                            Text(dismissButtonText, color = textButtonColor)
                        }
                    }
                    if (onConfirmButtonClicked != null) {
                        TextButton(
                            enabled = minLength == -1 || textFieldValue.text.length >= minLength,
                            onClick = {
                                onConfirmButtonClicked(textFieldValue.text)
                            }
                        ) {
                            Text(confirmButtonText, color = textButtonColor)
                        }
                    }
                }
            }
        }
    }
}

@Suppress("LongMethod")
@Composable
fun TwoInputDialog(
    onDismissRequest: (() -> Unit) = {},
    title: String? = null,
    textFieldLabelFirst: String? = null,
    initialTextFieldTextFirst: String? = null,
    textFieldLabelSecond: String? = null,
    initialTextFieldTextSecond: String? = null,
    confirmButtonText: String = stringResource(R.string.ok),
    onConfirmButtonClicked: ((Pair<String, String>) -> Unit)? = null,
    dismissButtonText: String = stringResource(R.string.cancel),
    onDismissButtonClicked: (() -> Unit)? = null,
    minLengthFirst: Int = -1,
    maxLengthFirst: Int = -1,
    minLengthSecond: Int = -1,
    maxLengthSecond: Int = -1,
    properties: DialogProperties = DialogProperties(),
    shape: Shape = DialogDefaults.DefaultCorner,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textButtonColor: Color = MaterialTheme.colorScheme.primary, // This is specifically for handling theming in this app. May not want in Commons.
) {
    var textFieldValueFirst by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(initialTextFieldTextFirst ?: "", TextRange(initialTextFieldTextFirst?.length ?: 0)))
    }
    var textFieldValueSecond by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(initialTextFieldTextSecond ?: "", TextRange(initialTextFieldTextSecond?.length ?: 0)))
    }

    // Test: request focus on TextField
    val focusRequester = remember { FocusRequester() }
    val (item1, item2) = remember { FocusRequester.createRefs() }

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
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }

                val keyboardController = LocalSoftwareKeyboardController.current

                // First Text Field
                DayNightTextField(
                    value = textFieldValueFirst,
                    onValueChange = { newTextFieldValue ->
                        val newText = if (maxLengthFirst > 0) {
                            newTextFieldValue.text.take(maxLengthFirst)
                        } else {
                            newTextFieldValue.text
                        }

                        textFieldValueFirst = newTextFieldValue.copy(newText, selection = TextRange(newText.length))
                    },
                    label = if (textFieldLabelFirst != null) {
                        { Text(textFieldLabelFirst) }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .focusOrder(item1) {
                            next = item2
                            down = item2
                            previous = item2
                            up = item2
                        },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )

                // Second Text Field
                DayNightTextField(
                    value = textFieldValueSecond,
                    onValueChange = { newTextFieldValue ->
                        val newText = if (maxLengthSecond > 0) {
                            newTextFieldValue.text.take(maxLengthSecond)
                        } else {
                            newTextFieldValue.text
                        }

                        textFieldValueSecond = newTextFieldValue.copy(newText, selection = TextRange(newText.length))
                    },
                    label = if (textFieldLabelSecond != null) {
                        { Text(textFieldLabelSecond) }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .focusOrder(item2) {
                            next = item1
                            down = item1
                            previous = item1
                            up = item1
                        },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )

                LaunchedEffect(Unit) {
                    delay(200) // Need to have a small delay or the keyboard does not appear on the focus: https://issuetracker.google.com/issues/204502668
                    focusRequester.requestFocus()
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
                            }
                        ) {
                            Text(dismissButtonText, color = textButtonColor)
                        }
                    }
                    if (onConfirmButtonClicked != null) {
                        TextButton(
                            enabled = (minLengthFirst == -1 || textFieldValueFirst.text.length >= minLengthFirst) && (minLengthSecond == -1 || textFieldValueSecond.text.length >= minLengthSecond),
                            onClick = {
                                onConfirmButtonClicked(Pair(textFieldValueFirst.text, textFieldValueSecond.text))
                            }
                        ) {
                            Text(confirmButtonText, color = textButtonColor)
                        }
                    }
                }
            }
        }
    }
}

data class InputDialogData(
    override val visible: Boolean = false,
    val title: String? = null,
    val initialTextFieldText: String? = null
): DialogData

data class TwoInputDialogData(
    override val visible: Boolean = false,
    val title: String? = null,
    val initialTextFieldTextFirst: String? = null,
    val initialTextFieldTextSecond: String? = null
): DialogData

@Preview(group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Composable
private fun TestInputDialog() {
    AppTheme {
        InputDialog(
            title = "Title",
            initialTextFieldText = "Default Value",
            onConfirmButtonClicked = { },
            onDismissButtonClicked = { },
            minLength = 1,
            maxLength = 20
        )
    }
}

@Preview(group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Composable
private fun TestTwoInputDialog() {
    AppTheme {
        TwoInputDialog(
            title = "Title",
            initialTextFieldTextFirst = "First",
            initialTextFieldTextSecond = "Second",
            onConfirmButtonClicked = { },
            onDismissButtonClicked = { }
        )
    }
}
