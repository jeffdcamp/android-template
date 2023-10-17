package org.jdc.template.ui.compose.form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import org.jdc.template.ui.compose.util.DateUiUtil

@Composable
fun DateClickableTextField(
    label: String,
    localDateFlow: StateFlow<LocalDate?>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorTextFlow: StateFlow<String?> = MutableStateFlow<String?>(null)
) {
    val date by localDateFlow.collectAsStateWithLifecycle()
    val text = DateUiUtil.getLocalDateText(LocalContext.current, date)
    val errorText by errorTextFlow.collectAsStateWithLifecycle()

    ClickableTextField(
        label = label,
        text = text,
        supportingText = errorText,
        isError = !errorText.isNullOrBlank(),
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun DateClickableTextField(
    label: String,
    date: LocalDate,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorText: String? = null
) {
    val text = DateUiUtil.getLocalDateText(LocalContext.current, date)

    ClickableTextField(
        label = label,
        text = text,
        supportingText = errorText,
        isError = !errorText.isNullOrBlank(),
        onClick = onClick,
        modifier = modifier
    )
}
