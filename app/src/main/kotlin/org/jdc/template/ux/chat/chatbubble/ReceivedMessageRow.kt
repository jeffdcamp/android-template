package org.jdc.template.ux.chat.chatbubble

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.theme.AppTheme

@Composable
fun ReceivedMessageRow(
    text: String? = null,
    messageTime: String? = null,
    aboveTextContent: (@Composable () -> Unit)? = null,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = 8.dp,
                end = 64.dp,
                top = 4.dp,
                bottom = 4.dp
            )
    ) {
        //ChatBubble
        ChatBubbleConstraints(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomEnd = 16.dp, topEnd = 16.dp, bottomStart = 16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { }
        ) {
            aboveTextContent?.invoke()

            if (text.isNullOrBlank()) {
                MessageTimeText(
                    messageTime = messageTime,
                    modifier = Modifier
                        .wrapContentSize(Alignment.CenterEnd)
                        .padding(end = 8.dp, bottom = 4.dp),
                )
            } else {
                TextMessageInsideBubble(
                    modifier = Modifier.padding(
                        start = 4.dp,
                        top = 4.dp,
                        end = 8.dp,
                        bottom = 4.dp
                    ),
                    text = text,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge
                ) {
                    MessageTimeText(
                        messageTime = messageTime,
                        modifier = Modifier.wrapContentSize(),
                    )
                }
            }
        }
    }
}

@PreviewDefault
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            Column {
                ReceivedMessageRow("", messageTime = "12:00PM", aboveTextContent = @Composable { Icon(modifier = Modifier.size(200.dp), imageVector = Icons.Default.Image, contentDescription = null) })
                ReceivedMessageRow("Hello World", messageTime = "Time")
                ReceivedMessageRow("Hello this represents a really long text and see what happens if it goes multiple lines.", messageTime = "Time")
            }
        }
    }
}