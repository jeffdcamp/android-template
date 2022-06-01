package org.jdc.template.ui.compose.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jdc.template.ui.compose.AutoSizeText

@Composable
fun AppBarTitle(
    title: String,
    subtitle: String? = null,
    autoSizeTitle: Boolean = false
) {
    Column(verticalArrangement = Arrangement.Center) {
        // title
        if (!autoSizeTitle) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1
            )
        } else {
            AutoSizeText(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1
            )
        }

        // subtitle
        if (!subtitle.isNullOrBlank()) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1
            )
        }
    }
}