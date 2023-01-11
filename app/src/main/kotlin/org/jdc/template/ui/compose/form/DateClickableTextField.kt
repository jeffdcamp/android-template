package org.jdc.template.ui.compose.form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.StateFlow
import org.jdc.template.ui.DateUiUtil
import java.time.LocalDate

@Composable
fun DateClickableTextField(label: String, localDateFlow: StateFlow<LocalDate?>, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val date by localDateFlow.collectAsState()
    val text = DateUiUtil.getLocalDateText(LocalContext.current, date)
    ClickableTextField(label, text, onClick, modifier)
}