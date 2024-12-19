package org.jdc.template.ux.chat.chatbubble

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jdc.template.R
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.compose.appbar.AppBarMenuItem.OverflowMenuItem
import org.jdc.template.ui.compose.menu.OverflowMenuItemsContent
import org.jdc.template.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SentMessageRow(
    text: String? = null,
    messageTime: String? = null,
    messageStatus: MessageStatus = MessageStatus.NONE,
    onDeleteClicked: (() -> Unit)? = null,
    aboveTextContent: (@Composable () -> Unit)? = null,
) {
    val expanded = remember { mutableStateOf(false) }

    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = 64.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
    ) {
        ChatBubbleConstraints(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .combinedClickable(
                    onClick = { },
                    onLongClick = { expanded.value = true },
                )

        ) {
            aboveTextContent?.invoke()

            if (text.isNullOrBlank()) {
                MessageTimeText(
                    messageTime = messageTime,
                    modifier = Modifier
                        .wrapContentSize(Alignment.CenterEnd)
                        .padding(end = 4.dp),
                )
            } else {
                TextMessageInsideBubble(
                    modifier = Modifier.padding(
                        start = 8.dp,
                        top = 4.dp,
                        end = 4.dp,
                        bottom = 4.dp
                    ),
                    text = text,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyLarge
                ) {
                    MessageTimeText(
                        messageTime = messageTime,
                        messageStatus = messageStatus,
                        modifier = Modifier.wrapContentSize(),
                    )
                }
            }
        }

        if (onDeleteClicked != null) {
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }) {

                val menuItems = listOf(
                    OverflowMenuItem(textId = R.string.delete, action = { onDeleteClicked() })
                )
                OverflowMenuItemsContent(menuItems, expanded)
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
                SentMessageRow("Hello World")
                ReceivedMessageRow("Hi", messageTime = "8:45")
                SentMessageRow("How are you today?", messageTime = "9:15", messageStatus = MessageStatus.RECEIVED)
                SentMessageRow("", messageTime = "12:00PM", aboveTextContent = @Composable { Icon(modifier = Modifier.size(200.dp), imageVector = Icons.Default.Image, contentDescription = null) })
                SentMessageRow("What are you doing?", messageTime = "10:00", messageStatus = MessageStatus.PENDING)
                ReceivedMessageRow("Doing GREAT, I'm going shopping", messageTime = "8:45")
                SentMessageRow("Love to come!", messageTime = "2:00", messageStatus = MessageStatus.READ)
                ReceivedMessageRow("OK!", messageTime = "8:45")
            }
        }
    }
}