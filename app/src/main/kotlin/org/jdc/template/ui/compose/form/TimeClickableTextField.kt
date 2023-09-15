package org.jdc.template.ui.compose.form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalTime
import org.jdc.template.ui.compose.util.DateUiUtil

@Composable
fun TimeClickableTextField(label: String, localTimeFlow: StateFlow<LocalTime?>, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val time by localTimeFlow.collectAsStateWithLifecycle()
    val text = DateUiUtil.getLocalTimeText(LocalContext.current, time)
    ClickableTextField(label, text, onClick, modifier)
}

@Composable
fun TimeClickableTextField(label: String, localTime: LocalTime, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val text = DateUiUtil.getLocalTimeText(LocalContext.current, localTime)
    ClickableTextField(label, text, onClick, modifier)
}
