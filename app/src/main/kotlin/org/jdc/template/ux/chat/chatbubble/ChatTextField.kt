package org.jdc.template.ux.chat.chatbubble

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import org.jdc.template.R
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.theme.AppTheme

@Composable
internal fun ChatTextField(
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier,
    textFieldLeadingIcon: @Composable (() -> Unit)? = null,
) {
    var messageText by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        TextField(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .weight(1f)
                .focusable(true),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            value = messageText,
            onValueChange = { messageText = it },
            maxLines = 4,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            placeholder = { Text(text = stringResource(id = R.string.message)) },
            leadingIcon = textFieldLeadingIcon,
            trailingIcon = {
                Row {
                    IconButton(onClick = {
                        onSendMessage(messageText)
                        messageText = ""
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.Send, contentDescription = stringResource(R.string.send_message))
                    }
                }
            }
        )
    }
}

@PreviewDefault
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            ChatTextField(
                onSendMessage = {}
            )
        }
    }
}
