package org.jdc.template.ui.compose.form

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.ui.compose.DayNightTextField
import org.jdc.template.ui.compose.util.formKeyEventHandler

@Composable
fun TextFieldDataTextField(
    label: String,
    textFlow: StateFlow<TextFieldData>,
    testTag: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val textFieldValue by textFlow.collectAsState()
    val focusManager = LocalFocusManager.current

    DayNightTextField(
        value = textFieldValue.text,
        onValueChange = { onChange(it) },
        label = { Text(label) },
        singleLine = true,
        isError = textFieldValue.isError,
        supportingText = textFieldValue.supportingText?.let{ { Text(it) } },
        modifier = modifier
            .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
            .testTag(testTag)
    )
}