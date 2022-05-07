package org.jdc.template.ui.compose.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface DialogUiState<T> {
    val visible: Boolean
    val onConfirm: ((T) -> Unit)?
    val onDismiss: (() -> Unit)?
    val onDismissRequest: (() -> Unit)?
}

class DismissedDialogUiState(
    override val visible: Boolean = false,
    override val onConfirm: ((Unit) -> Unit)? = null,
    override val onDismiss: (() -> Unit)? = null,
    override val onDismissRequest: (() -> Unit)? = null
) : DialogUiState<Unit>

@Composable
fun <T : DialogUiState<*>> HandleDialogUiState(
    dialogUiStateFlow: StateFlow<T?>,
    dialog: @Composable (T) -> Unit = { dialogUiState -> LibraryDialogs(dialogUiState) }
) {
    val dialogUiState by dialogUiStateFlow.collectAsState()

    dialogUiState?.let {
        if (it.visible) {
            dialog(it)
        }
    }
}

@Composable
fun LibraryDialogs(dialogUiState: DialogUiState<*>) {
    when (dialogUiState) {
        is MessageDialogUiState -> MessageDialog(dialogUiState)
        is InputDialogUiState -> InputDialog(dialogUiState)
        is TwoInputDialogUiState -> TwoInputDialog(dialogUiState)
        is RadioDialogUiState -> RadioDialog(dialogUiState)
        is DateDialogUiState -> MaterialDatePickerDialog(dialogUiState)
        is TimeDialogUiState -> MaterialTimePickerDialog(dialogUiState)
    }
}

fun ViewModel.showMessageDialog(
    dialogUiStateFlow: MutableStateFlow<DialogUiState<*>?>,
    title: String? = null,
    text: String? = null,
    confirmButtonText: String? = null,
    dismissButtonText: String? = null,
    onConfirm: () -> Unit = {},
    onDismiss: (() -> Unit)? = null
) {
    dialogUiStateFlow.value = MessageDialogUiState(
        title = title,
        text = text,
        confirmButtonText = confirmButtonText,
        dismissButtonText = dismissButtonText,
        onConfirm = {
            onConfirm()
            dismissDialog(dialogUiStateFlow)
        },
        onDismiss = if (onDismiss != null) {
            {
                onDismiss()
                dismissDialog(dialogUiStateFlow)
            }
        } else {
            null
        },
        onDismissRequest = {
            dismissDialog(dialogUiStateFlow)
        }
    )
}

fun ViewModel.dismissDialog(
    dialogUiStateFlow: MutableStateFlow<DialogUiState<*>?>
) {
    dialogUiStateFlow.value = DismissedDialogUiState()
}
