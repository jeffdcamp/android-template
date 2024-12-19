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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.MutableStateFlow
import org.jdc.template.model.domain.ChatMessage
import org.jdc.template.model.domain.inline.ChatThreadId
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.compose.appbar.AppBarTitle
import org.jdc.template.ui.compose.dialog.HandleDialogUiState
import org.jdc.template.ui.navigation.HandleNavigation
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.util.time.TimeFormatUtil
import org.jdc.template.ux.MainAppScaffoldWithNavBar
import org.jdc.template.ux.chat.chatbubble.ChatTextField
import org.jdc.template.ux.chat.chatbubble.ReceivedMessageRow
import org.jdc.template.ux.chat.chatbubble.SentMessageRow

@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val threadName by uiState.threadNameFlow.collectAsStateWithLifecycle()

    MainAppScaffoldWithNavBar(
        title = {
            AppBarTitle(
                title = "Messages",
                subtitle = threadName,
            )
        },
        onNavigationClick = { navController.popBackStack() },
    ) {
        ChatScreenContent(uiState)
    }

    HandleDialogUiState(uiState.dialogUiStateFlow)
    HandleNavigation(viewModel, navController)
}

@Composable
private fun ChatScreenContent(
    uiState: ChatUiState,
) {
    val fromPerspectiveUserId by uiState.fromPerspectiveUserId.collectAsStateWithLifecycle()
    val messagesPagingData = uiState.allMessagesPagingFlow.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // Add box to allow LazyColumn to anchor to to the bottom of the message area
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
                            fromPerspectiveUserId = fromPerspectiveUserId,
                            message = message,
                            onDeleteClick = { uiState.onDeleteClick(message.id) }
                        )
                    }
                }
            }
        }

        ChatTextField(
            onSendMessage = uiState.onSendClick,
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

    val uiState = ChatUiState(
        threadNameFlow = MutableStateFlow<String>("Test"),
        fromPerspectiveUserId = MutableStateFlow<IndividualId>(IndividualId("1")),
        allMessagesPagingFlow = MutableStateFlow(value = PagingData.from(messages)),
        onSendClick = {},
        onDeleteClick = {},
    )

    AppTheme {
        Surface {
            ChatScreenContent(uiState)
        }
    }
}
