package org.jdc.template.ui.compose.dialog

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object DialogDefaults {
    val DefaultCorner = RoundedCornerShape(28.0.dp) // from DialogTokens.ContainerShape
    val DialogPadding = PaddingValues(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 18.dp) // from AlertDialog.DialogPadding
}