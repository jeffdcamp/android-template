package org.jdc.template.ui.compose.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.StateFlow

interface DialogData {
    val visible: Boolean
}

@Composable
fun <T: DialogData> HandleDialog(
    dialogDataFlow: StateFlow<T?>,
    dialog: @Composable (T) -> Unit
) {
    val dialogData by dialogDataFlow.collectAsState()

    dialogData?.let {
        if (it.visible) {
            dialog(it)
        }
    }
}
