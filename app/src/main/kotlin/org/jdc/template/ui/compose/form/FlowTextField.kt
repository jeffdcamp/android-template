package org.jdc.template.ui.compose.form

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.ui.compose.DayNightPasswordTextField
import org.jdc.template.ui.compose.DayNightTextField
import org.jdc.template.ui.compose.util.formKeyEventHandler

@Composable
fun FlowTextField(
    label: String,
    textFlow: StateFlow<String>,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorTextFlow: StateFlow<String?> = MutableStateFlow(null),
) {
    val text by textFlow.collectAsStateWithLifecycle()
    val errorText by errorTextFlow.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    DayNightTextField(
        value = text,
        onValueChange = { onChange(it) },
        label = { Text(label) },
        singleLine = true,
        isError = !errorText.isNullOrBlank(),
        supportingText = errorText?.let{ { Text(it) } },
        modifier = modifier
            .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
    )
}

@Composable
fun PasswordFlowTextField(
    label: String,
    textFlow: StateFlow<String>,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorTextFlow: StateFlow<String?> = MutableStateFlow(null),
) {
    val text by textFlow.collectAsStateWithLifecycle()
    val errorText by errorTextFlow.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    DayNightPasswordTextField(
        value = text,
        onValueChange = { onChange(it) },
        label = { Text(label) },
        isError = !errorText.isNullOrBlank(),
        supportingText = errorText?.let{ { Text(it) } },
        modifier = modifier
            .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
    )
}
