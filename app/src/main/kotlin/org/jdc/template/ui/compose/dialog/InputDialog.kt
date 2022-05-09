package org.jdc.template.ui.compose.dialog

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import org.jdc.template.ui.compose.DayNightTextField
import org.jdc.template.ui.theme.AppTheme
import java.util.Locale

@Composable
fun InputDialog(
    onDismissRequest: (() -> Unit) = {},
    title: String? = null,
    textFieldLabel: String? = null,
    initialTextFieldText: String? = null,
    confirmButtonText: String = stringResource(android.R.string.ok),
    onConfirmButtonClicked: ((String) -> Unit)? = null,
    dismissButtonText: String = stringResource(android.R.string.cancel),
    onDismissButtonClicked: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    minLength: Int = -1,
    maxLength: Int = -1,
    properties: DialogProperties = DialogProperties(),
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    textButtonColor: Color = MaterialTheme.colors.primary, // This is specifically for handling theming in this app. May not want in Commons.
) {
    var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue(initialTextFieldText ?: "", TextRange(initialTextFieldText?.length ?: 0))) }

    val focusRequester = remember { FocusRequester() }

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

                        textFieldValue = newTextFieldValue.copy(newText)
                    },
                    label = if (textFieldLabel != null) {
                        { Text(textFieldLabel) }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .focusRequester(focusRequester),
                    singleLine = singleLine,
                    keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Done),
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
                        .padding(all = 8.dp)

                ) {
                    if (onDismissButtonClicked != null) {
                        TextButton(
                            onClick = {
                                onDismissButtonClicked()
                            }
                        ) {
                            Text(dismissButtonText.uppercase(Locale.getDefault()), color = textButtonColor)
                        }
                    }
                    if (onConfirmButtonClicked != null) {
                        TextButton(
                            enabled = minLength == -1 || textFieldValue.text.length >= minLength,
                            onClick = {
                                onConfirmButtonClicked(textFieldValue.text)
                            }
                        ) {
                            Text(confirmButtonText.uppercase(Locale.getDefault()), color = textButtonColor)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InputDialog(
    dialogUiState: InputDialogUiState
){
    InputDialog(
        onDismissRequest = { dialogUiState.onDismissRequest() },
        title = dialogUiState.title,
        textFieldLabel = dialogUiState.textFieldLabel,
        initialTextFieldText = dialogUiState.initialTextFieldText,
        confirmButtonText = dialogUiState.confirmButtonText ?: stringResource(android.R.string.ok),
        onConfirmButtonClicked = { dialogUiState.onConfirm(it) },
        dismissButtonText = dialogUiState.dismissButtonText ?: stringResource(android.R.string.cancel),
        onDismissButtonClicked = { dialogUiState.onDismiss() },
        keyboardOptions = dialogUiState.keyboardOptions ?: KeyboardOptions.Default,
        singleLine = dialogUiState.singleLine,
        minLength = dialogUiState.minLength,
        maxLength = dialogUiState.maxLength,
        properties = DialogProperties(),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
        textButtonColor = MaterialTheme.colors.primary, // This is specifically for handling theming in this app. May not want in Commons.
    )
}

data class InputDialogUiState(
    val title: String? = null,
    val textFieldLabel: String? = null,
    val initialTextFieldText: String? = null,
    val confirmButtonText: String? = null,
    val dismissButtonText: String? = null,
    val keyboardOptions: KeyboardOptions? = null,
    val singleLine: Boolean = true,
    val minLength: Int = -1,
    val maxLength: Int = -1,
    override val onConfirm: (String) -> Unit = {},
    override val onDismiss: () -> Unit = {},
    override val onDismissRequest: () -> Unit = {},
) : DialogUiState<String>

@Suppress("LongMethod")
@Composable
fun TwoInputDialog(
    onDismissRequest: (() -> Unit) = {},
    title: String? = null,
    textFieldLabelFirst: String? = null,
    initialTextFieldTextFirst: String? = null,
    textFieldLabelSecond: String? = null,
    initialTextFieldTextSecond: String? = null,
    confirmButtonText: String = stringResource(android.R.string.ok),
    onConfirmButtonClicked: ((Pair<String, String>) -> Unit)? = null,
    dismissButtonText: String = stringResource(android.R.string.cancel),
    onDismissButtonClicked: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    minLengthFirst: Int = -1,
    maxLengthFirst: Int = -1,
    minLengthSecond: Int = -1,
    maxLengthSecond: Int = -1,
    properties: DialogProperties = DialogProperties(),
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    textButtonColor: Color = MaterialTheme.colors.primary, // This is specifically for handling theming in this app. May not want in Commons.
) {
    var textFieldValueFirst by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(initialTextFieldTextFirst ?: "", TextRange(initialTextFieldTextFirst?.length ?: 0)))
    }
    var textFieldValueSecond by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(initialTextFieldTextSecond ?: "", TextRange(initialTextFieldTextSecond?.length ?: 0)))
    }

    val (item1, item2) = remember { FocusRequester.createRefs() }

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

                        textFieldValueFirst = newTextFieldValue.copy(newText)
                    },
                    label = if (textFieldLabelFirst != null) {
                        { Text(textFieldLabelFirst) }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .focusRequester(item1)
                        .focusOrder(item1) {
                            next = item2
                            down = item2
                            previous = item2
                            up = item2
                        },
                    singleLine = singleLine,
                    keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Next),
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

                        textFieldValueSecond = newTextFieldValue.copy(newText)
                    },
                    label = if (textFieldLabelSecond != null) {
                        { Text(textFieldLabelSecond) }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .focusOrder(item2) {
                            next = item1
                            down = item1
                            previous = item1
                            up = item1
                        },
                    singleLine = singleLine,
                    keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )

                LaunchedEffect(Unit) {
                    delay(200) // Need to have a small delay or the keyboard does not appear on the focus: https://issuetracker.google.com/issues/204502668
                    item1.requestFocus()
                }

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
                            Text(dismissButtonText.uppercase(Locale.getDefault()), color = textButtonColor)
                        }
                    }
                    if (onConfirmButtonClicked != null) {
                        TextButton(
                            enabled = (minLengthFirst == -1 || textFieldValueFirst.text.length >= minLengthFirst) && (minLengthSecond == -1 || textFieldValueSecond.text.length >= minLengthSecond),
                            onClick = {
                                onConfirmButtonClicked(Pair(textFieldValueFirst.text, textFieldValueSecond.text))
                            }
                        ) {
                            Text(confirmButtonText.uppercase(Locale.getDefault()), color = textButtonColor)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TwoInputDialog(
    dialogUiState: TwoInputDialogUiState
){
    TwoInputDialog(
        onDismissRequest = { dialogUiState.onDismissRequest() },
        title = dialogUiState.title,
        textFieldLabelFirst = dialogUiState.textFieldLabelFirst,
        initialTextFieldTextFirst = dialogUiState.initialTextFieldTextFirst,
        textFieldLabelSecond = dialogUiState.textFieldLabelSecond,
        initialTextFieldTextSecond = dialogUiState.initialTextFieldTextSecond,
        confirmButtonText = dialogUiState.confirmButtonText ?: stringResource(android.R.string.ok),
        onConfirmButtonClicked = { dialogUiState.onConfirm(it) },
        dismissButtonText = dialogUiState.dismissButtonText ?: stringResource(android.R.string.cancel),
        onDismissButtonClicked = { dialogUiState.onDismiss() },
        keyboardOptions = dialogUiState.keyboardOptions ?: KeyboardOptions.Default,
        singleLine = dialogUiState.singleLine,
        minLengthFirst = dialogUiState.minLengthSecond,
        maxLengthFirst = dialogUiState.maxLengthSecond,
        properties = DialogProperties(),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
        textButtonColor = MaterialTheme.colors.primary, // This is specifically for handling theming in this app. May not want in Commons.
    )
}

data class TwoInputDialogUiState(
    val title: String? = null,
    val textFieldLabelFirst: String? = null,
    val initialTextFieldTextFirst: String? = null,
    val textFieldLabelSecond: String? = null,
    val initialTextFieldTextSecond: String? = null,
    val confirmButtonText: String? = null,
    val dismissButtonText: String? = null,
    val keyboardOptions: KeyboardOptions? = null,
    val singleLine: Boolean = true,
    val minLengthFirst: Int = -1,
    val maxLengthFirst: Int = -1,
    val minLengthSecond: Int = -1,
    val maxLengthSecond: Int = -1,
    override val onConfirm: (Pair<String, String>) -> Unit = {},
    override val onDismiss: () -> Unit = {},
    override val onDismissRequest: () -> Unit = {},
) : DialogUiState<Pair<String, String>>

@Preview(group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Composable
private fun PreviewInputDialog() {
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
private fun PreviewTwoInputDialog() {
    AppTheme() {
        TwoInputDialog(
            title = "Title",
            initialTextFieldTextFirst = "First",
            initialTextFieldTextSecond = "Second",
            onConfirmButtonClicked = { },
            onDismissButtonClicked = { }
        )
    }
}
