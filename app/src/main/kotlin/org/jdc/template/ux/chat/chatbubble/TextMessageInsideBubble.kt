package org.jdc.template.ux.chat.chatbubble

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.theme.AppTheme

@Composable
fun TextMessageInsideBubble(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current,
    messageStatContent: @Composable () -> Unit = { Text(text = "") },
) {
    val chatRowData = remember { ChatRowData() }
    val testAndMessageStatContent = @Composable {
        Text(
            modifier = modifier
                .wrapContentSize(),
            text = text,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            style = style,
            maxLines = maxLines,
            onTextLayout = { textLayoutResult: TextLayoutResult ->
                chatRowData.lineCount = textLayoutResult.lineCount
                chatRowData.lastLineWidth = textLayoutResult.getLineRight(chatRowData.lineCount - 1)
                chatRowData.textWidth = textLayoutResult.size.width
            }
        )

        messageStatContent()
    }

    Layout(
        modifier = modifier,
        content = testAndMessageStatContent
    ) { measurables: List<Measurable>, constraints: Constraints ->
        require(measurables.size == 2) { "There should be 2 components for this layout (text and messageStat)" }

        val placeables: List<Placeable> = measurables.map { measurable ->
            // Measure each child maximum constraints since message can cover all of the available
            // space by parent
            measurable.measure(Constraints(0, constraints.maxWidth))
        }

        val message = placeables.first()
        val status = placeables.last()

        // calculate chat row dimensions are not  based on message and status positions
        if ((chatRowData.rowWidth == 0 || chatRowData.rowHeight == 0) || chatRowData.text != text) {
            // Constrain with max width instead of longest sibling
            // since this composable can be longest of siblings after calculation
            chatRowData.parentWidth = constraints.maxWidth
            calculateChatWidthAndHeight(chatRowData, message, status)
            // Parent width of this chat row is either result of width calculation
            // or quote or other sibling width if they are longer than calculated width.
            // minWidth of Constraint equals (text width + horizontal padding)
            chatRowData.parentWidth = chatRowData.rowWidth.coerceAtLeast(minimumValue = constraints.minWidth)
        }

        layout(
            width = chatRowData.parentWidth,
            height = chatRowData.rowHeight
        ) {
            message.placeRelative(0, 0)
            // set left of status relative to parent because other elements could result this row
            // to be long as longest composable
            status.placeRelative(
                x = chatRowData.parentWidth - status.width,
                y = chatRowData.rowHeight - status.height
            )
        }
    }
}

private data class ChatRowData(
    var text: String = "",
    // Width of the text without padding
    var textWidth: Int = 0,
    var lastLineWidth: Float = 0f,
    var lineCount: Int = 0,
    var rowWidth: Int = 0,
    var rowHeight: Int = 0,
    var parentWidth: Int = 0,
    var measuredType: Int = 0,
)


private fun calculateChatWidthAndHeight(
    chatRowData: ChatRowData,
    message: Placeable,
    status: Placeable?,
) {

    if (status != null) {
        val lineCount = chatRowData.lineCount
        val lastLineWidth = chatRowData.lastLineWidth
        val parentWidth = chatRowData.parentWidth

        val padding = (message.measuredWidth - chatRowData.textWidth) / 2


        // Multiple lines and last line and status is longer than text size and right padding
        when {
            lineCount > 1 && lastLineWidth + status.measuredWidth >= chatRowData.textWidth + padding -> {
                chatRowData.rowWidth = message.measuredWidth
                chatRowData.rowHeight = message.measuredHeight + status.measuredHeight
                chatRowData.measuredType = 0
            }
            lineCount > 1 && lastLineWidth + status.measuredWidth < chatRowData.textWidth + padding -> {
                // Multiple lines and last line and status is shorter than text size and right padding
                chatRowData.rowWidth = message.measuredWidth
                chatRowData.rowHeight = message.measuredHeight
                chatRowData.measuredType = 1
            }
            lineCount == 1 && message.width + status.measuredWidth >= parentWidth -> {
                chatRowData.rowWidth = message.measuredWidth
                chatRowData.rowHeight = message.measuredHeight + status.measuredHeight
                chatRowData.measuredType = 2
            }
            else -> {
                chatRowData.rowWidth = message.measuredWidth + status.measuredWidth
                chatRowData.rowHeight = message.measuredHeight
                chatRowData.measuredType = 3
            }
        }
    } else {
        chatRowData.rowWidth = message.width
        chatRowData.rowHeight = message.height
    }
}

@PreviewDefault
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            TextMessageInsideBubble(
                text = "Hello World",
                messageStatContent = { Text(text = "Time") },
            )
        }
    }
}
