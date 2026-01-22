package org.jdc.template.ux.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.MutableStateFlow
import org.jdc.template.R
import org.jdc.template.shared.model.domain.ChatMessage
import org.jdc.template.shared.model.domain.inline.ChatMessageId
import org.jdc.template.shared.model.domain.inline.ChatThreadId
import org.jdc.template.shared.model.domain.inline.IndividualId
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.compose.appbar.AppBarTitle
import org.jdc.template.ui.compose.dialog.HandleDialogUiState
import org.jdc.template.ui.navigation3.HandleNavigation3
import org.jdc.template.ui.navigation3.navigator.Navigation3Navigator
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.util.time.TimeFormatUtil
import org.jdc.template.ux.MainAppScaffoldWithNavBar
import org.jdc.template.ux.chat.chatbubble.ChatTextField
import org.jdc.template.ux.chat.chatbubble.ReceivedMessageRow
import org.jdc.template.ux.chat.chatbubble.SentMessageRow

@Composable
fun ChatScreen(
    navigator: Navigation3Navigator,
    viewModel: ChatViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val threadName = (uiState as? ChatUiState.Ready)?.threadName.orEmpty()

    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = {
            AppBarTitle(
                title = stringResource(R.string.messages),
                subtitle = threadName,
            )
        },
        onNavigationClick = { navigator.pop() },
    ) {
        when (val uiState = uiState) {
            ChatUiState.Loading -> {}
            is ChatUiState.Ready -> {
                ChatScreenContent(
                    uiState = uiState,
                    onSendClick = viewModel::onMessageSend,
                    onDeleteClick = viewModel::onDeleteMessage
                )
            }
            ChatUiState.Empty -> {}
        }

    }

    HandleDialogUiState(viewModel.dialogUiStateFlow)
    HandleNavigation3(viewModel, navigator)
}

@Composable
private fun ChatScreenContent(
    uiState: ChatUiState.Ready,
    onSendClick: (String) -> Unit,
    onDeleteClick: (ChatMessageId) -> Unit,
) {
    val messagesPagingData = uiState.allMessagesPagingFlow.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Add box to allow LazyColumn to anchor to the bottom of the message area
        Box(
            modifier = Modifier.weight(1f),
        ) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                reverseLayout = true,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                items(
                    count = messagesPagingData.itemCount,
                    key = messagesPagingData.itemKey(),
                    contentType = messagesPagingData.itemContentType()
                ) { index ->
                    val message = messagesPagingData[index]

                    if (message != null) {
                        MessageItem(
                            fromPerspectiveUserId = uiState.fromPerspectiveUserId,
                            message = message,
                            onDeleteClick = { onDeleteClick(message.id) }
                        )
                    }
                }
            }
        }

        ChatTextField(
            onSendMessage = onSendClick,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun MessageItem(fromPerspectiveUserId: IndividualId, message: ChatMessage, onDeleteClick: () -> Unit) {
    val context = LocalContext.current

    if (message.individualId == fromPerspectiveUserId) {
        SentMessageRow(
            text = message.message,
            messageTime = TimeFormatUtil.formatMessageTime(context, message.createdDate),
            onDeleteClicked = onDeleteClick
        )
    } else {
        ReceivedMessageRow(
            text = message.message,
            messageTime = TimeFormatUtil.formatMessageTime(context, message.createdDate),
        )
    }
}

@PreviewDefault
@Composable
private fun Preview() {
    val messageItem = ChatMessage(
        chatThreadId = ChatThreadId("1"),
        message = "Hello World",
        individualId = IndividualId("1")
    )

    val messages = listOf(
        messageItem,
    )

    val uiState = ChatUiState.Ready(
        threadName = "Test",
        fromPerspectiveUserId = IndividualId("1"),
        allMessagesPagingFlow = MutableStateFlow(value = PagingData.from(messages)),
    )

    AppTheme {
        Surface {
            ChatScreenContent(uiState, {}, {})
        }
    }
}
