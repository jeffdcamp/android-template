package org.jdc.template.ui.compose.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.theme.AppTheme

@Composable
fun MenuOptionsDialog(
    onDismissRequest: (() -> Unit),
    title: String? = null,
    supportingText: String? = null,
    options: List<MenuOptionsDialogItem>,
    properties: DialogProperties = DialogProperties(),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = backgroundColor,
            tonalElevation = tonalElevation,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                // Title
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Supporting Text
                if (supportingText != null) {
                    Text(
                        text = supportingText,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Options
                options.forEach { menuOptionsDialogItem: MenuOptionsDialogItem ->
                    ListItem(
                        headlineContent = { Text(menuOptionsDialogItem.text()) },
                        modifier = Modifier
                            .clickable { menuOptionsDialogItem.onClick() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

data class MenuOptionsDialogItem(val text: @Composable () -> String, val onClick: () -> Unit)

@Composable
fun MenuOptionsDialog(
    dialogUiState: MenuOptionsDialogUiState
) {
    MenuOptionsDialog(
        onDismissRequest = dialogUiState.onDismissRequest,
        title = dialogUiState.title?.invoke(),
        supportingText = dialogUiState.supportingText?.invoke(),
        options = dialogUiState.options
    )
}

data class MenuOptionsDialogUiState(
    val title: @Composable (() -> String)? = null,
    val supportingText: @Composable (() -> String)? = null,
    val options: List<MenuOptionsDialogItem>,
    override val onConfirm: ((String) -> Unit)? = null, // not used in OptionsDialog
    override val onDismiss: (() -> Unit)? = null,  // not used in OptionsDialog
    override val onDismissRequest: () -> Unit = {}
) : DialogUiState<String>

@PreviewDefault
@Composable
private fun Preview() {
    AppTheme {
        MenuOptionsDialog(
            onDismissRequest = {},
            title = "This is some really long text that should wrap and make for a long read, but will test the spacing of text.",
            supportingText = "This is some really long text that should wrap and make for a long read, but will test the spacing of text.",
            options = listOf(
                MenuOptionsDialogItem({ "Option 1" }) {},
                MenuOptionsDialogItem({ "Option 2" }) {},
                MenuOptionsDialogItem({ "Option 3" }) {},
            )
        )
    }
}
