package org.jdc.template.ui.compose.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListItemTextHeader(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    style: TextStyle = MaterialTheme.typography.titleSmall,
    textPadding: PaddingValues = PaddingValues(start = 16.dp, top = 16.dp),
) {
    Surface(modifier) {
        Column {
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(textPadding),
                color = color,
                style = style
            )
        }
    }
}

@Preview
@Composable
private fun ListItemTextHeaderPreview() {
    MaterialTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(start = 16.dp)
            ) {
                ListItemTextHeader(text = "Food")
                ListItem(headlineContent = { Text("Pizza") })
                ListItem(headlineContent = { Text("Taco") })
                ListItem(headlineContent = { Text("Hot Dog") })
                ListItem(headlineContent = { Text("Banana") })

                ListItemTextHeader(text = "Colors")
                ListItem(headlineContent = { Text("Red") })
                ListItem(headlineContent = { Text("Green") })
                ListItem(headlineContent = { Text("Blue") })
                ListItem(headlineContent = { Text("Yellow") })
            }
        }
    }
}
