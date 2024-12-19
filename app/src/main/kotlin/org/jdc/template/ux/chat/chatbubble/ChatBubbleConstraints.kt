package org.jdc.template.ux.chat.chatbubble

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize

@Composable
fun ChatBubbleConstraints(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        var recompositionIndex = 0
        var placeables: List<Placeable> = subcompose(recompositionIndex++, content).map {
            it.measure(constraints)
        }
        val columnSize =
            placeables.fold(IntSize.Zero) { currentMax: IntSize, placeable: Placeable ->
                IntSize(
                    width = maxOf(currentMax.width, placeable.width),
                    height = currentMax.height + placeable.height
                )
            }
        if (placeables.isNotEmpty() && (placeables.size > 1)) {
            placeables = subcompose(recompositionIndex, content).map { measurable: Measurable ->
                measurable.measure(Constraints(columnSize.width, constraints.maxWidth))
            }
        }
        layout(columnSize.width, columnSize.height) {
            var yPos = 0
            placeables.forEach { placeable: Placeable ->
                placeable.placeRelative(0, yPos)
                yPos += placeable.height
            }
        }
    }
}
