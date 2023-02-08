package org.jdc.template.ui.compose.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import org.jdc.template.ui.compose.AutoSizeText

@Composable
fun AppBarTitle(
    title: String,
    subtitle: String? = null,
    autoSizeTitle: Boolean = false,
    titleOverflow: TextOverflow = TextOverflow.Clip,
    subitleOverflow: TextOverflow = TextOverflow.Clip
) {
    Column(verticalArrangement = Arrangement.Center) {
        // title
        if (!autoSizeTitle) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = titleOverflow
            )
        } else {
            AutoSizeText(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                // overflow = TextOverflow.Clip  // NOTE: AutoSizeText can ONLY use TextOverflow.Clip
            )
        }

        // subtitle
        if (!subtitle.isNullOrBlank()) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = subitleOverflow
            )
        }
    }
}
