package org.jdc.template.ux.chat.chatbubble

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MessageTimeText(
    modifier: Modifier = Modifier,
    messageTime: String? = null,
    messageStatus: MessageStatus = MessageStatus.NONE,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (messageTime != null) {
            Text(
                text = messageTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }

        if (messageStatus != MessageStatus.NONE) {
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .padding(start = 4.dp),
                imageVector = Icons.Default.DoneAll,
                tint = when (messageStatus) {
                    MessageStatus.READ -> Color.Blue
                    else -> Color.Gray
                },
                contentDescription = "messageStatus"
            )
        }
    }
}
