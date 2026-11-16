package org.jdc.template.ui.compose.form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalTime
import org.jdc.template.ui.compose.util.DateUiUtil

@Composable
fun TimeClickableTextField(
    label: String,
    localTimeFlow: StateFlow<LocalTime?>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorTextFlow: StateFlow<String?> = MutableStateFlow<String?>(null)
) {
    val time by localTimeFlow.collectAsStateWithLifecycle()
    val errorText by errorTextFlow.collectAsStateWithLifecycle()

    TimeClickableTextField(
        label = label,
        localTime = time,
        onClick = onClick,
        modifier = modifier,
        errorText = errorText
    )
}

@Composable
fun TimeClickableTextField(
    label: String,
    localTime: LocalTime?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorText: String? = null
) {
    val text = DateUiUtil.getLocalTimeText(LocalContext.current, localTime)

    ClickableTextField(
        label = label,
        text = text,
        supportingText = errorText,
        isError = !errorText.isNullOrBlank(),
        onClick = onClick,
        modifier = modifier
    )
}
