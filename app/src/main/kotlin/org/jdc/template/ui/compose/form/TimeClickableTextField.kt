package org.jdc.template.ui.compose.form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.ui.compose.util.DateUiUtil
import java.time.LocalTime

@Composable
fun TimeClickableTextField(label: String, localTimeFlow: StateFlow<LocalTime?>, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val time by localTimeFlow.collectAsState()
    val text = DateUiUtil.getLocalTimeText(LocalContext.current, time)
    ClickableTextField(label, text, onClick, modifier)
}