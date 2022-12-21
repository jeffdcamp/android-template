package org.jdc.template.ui.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.jdc.template.ui.compose.util.formKeyEventHandler

@Composable
fun ClickableTextField(label: String, text: String, onClick: () -> Unit) {
    val source = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    DayNightTextField(
        value = text,
        onValueChange = { },
        readOnly = true,
        label = { Text(label) },
        interactionSource = source,
        modifier = Modifier
            .onPreviewKeyEvent { formKeyEventHandler(it, focusManager, onEnter = onClick) }
            .fillMaxWidth()
            .padding(top = 16.dp)
    )

    if (source.collectIsPressedAsState().value) {
        onClick()
    }
}