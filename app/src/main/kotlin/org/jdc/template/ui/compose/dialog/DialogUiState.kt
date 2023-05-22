package org.jdc.template.ui.compose.dialog

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface DialogUiState<T> {
    val onConfirm: ((T) -> Unit)?
    val onDismiss: (() -> Unit)?
    val onDismissRequest: (() -> Unit)?
}

@Composable
fun <T : DialogUiState<*>> HandleDialogUiState(
    dialogUiStateFlow: StateFlow<T?>,
    dialog: @Composable (T) -> Unit = { dialogUiState -> LibraryDialogs(dialogUiState) }
) {
    val dialogUiState by dialogUiStateFlow.collectAsStateWithLifecycle()

    dialogUiState?.let {
        dialog(it)
    }
}

@Composable
fun LibraryDialogs(dialogUiState: DialogUiState<*>) {
    when (dialogUiState) {
        is MessageDialogUiState -> MessageDialog(dialogUiState)
        is InputDialogUiState -> InputDialog(dialogUiState)
        is TwoInputDialogUiState -> TwoInputDialog(dialogUiState)
        is RadioDialogUiState -> RadioDialog(dialogUiState)
        is MenuOptionsDialogUiState -> MenuOptionsDialog(dialogUiState)
        is MultiSelectDialogUiState<*> -> MultiSelectDialog(dialogUiState)
        is DropDownMenuDialogUiState -> DropDownMenuDialog(dialogUiState)
        is DatePickerDialogUiState -> DatePickerDialog(dialogUiState)
        is DateRangePickerDialogUiState -> DateRangePickerDialog(dialogUiState)
        is TimePickerDialogUiState -> TimePickerDialog(dialogUiState)
    }
}

fun ViewModel.showMessageDialog(
    dialogUiStateFlow: MutableStateFlow<DialogUiState<*>?>,
    title: @Composable () -> String? = { null },
    text: @Composable () -> String? = { null },
    confirmButtonText: @Composable () -> String? = { stringResource(android.R.string.ok) },
    dismissButtonText: @Composable () -> String? = { stringResource(android.R.string.cancel) },
    onConfirm: (() -> Unit)? = {},
    onDismiss: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = { dismissDialog(dialogUiStateFlow) }
) {
    dialogUiStateFlow.value = MessageDialogUiState(
        title = title,
        text = text,
        confirmButtonText = confirmButtonText,
        dismissButtonText = dismissButtonText,
        onConfirm = if (onConfirm != null) {
            {
                onConfirm()
                dismissDialog(dialogUiStateFlow)
            }
        } else {
            null
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
            onDismissRequest?.invoke()
        }
    )
}

fun ViewModel.dismissDialog(
    dialogUiStateFlow: MutableStateFlow<DialogUiState<*>?>
) {
    dialogUiStateFlow.value = null
}
