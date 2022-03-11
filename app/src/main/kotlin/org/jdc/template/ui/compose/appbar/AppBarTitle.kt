package org.jdc.template.ui.compose.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jdc.template.ui.compose.AutoSizeText

@Composable
fun AppBarTitle(
    title: String,
    subtitle: String? = null,
    color: Color? = null,
    autoSizeTitle: Boolean = false
) {
    Column(verticalArrangement = Arrangement.Center) {
        // title
        if (!autoSizeTitle) {
            Text(
                text = title,
                color = color ?: Color.Unspecified,
                style = MaterialTheme.typography.h6,
                maxLines = 1
            )
        } else {
            AutoSizeText(
                text = title,
                color = color ?: Color.Unspecified,
                style = MaterialTheme.typography.h6,
                maxLines = 1
            )
        }

        // subtitle
        if (!subtitle.isNullOrBlank()) {
            Text(
                text = subtitle,
                color = color ?: Color.Unspecified,
                style = MaterialTheme.typography.caption,
                maxLines = 1
            )
        }
    }
}