package org.jdc.template.ux.about.typography

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.jdc.template.R
import org.jdc.template.ui.compose.PreviewDefault
import org.jdc.template.ui.navigation.navigator.Navigation3Navigator
import org.jdc.template.ui.theme.AppTheme
import org.jdc.template.ux.MainAppScaffoldWithNavBar

@Composable
fun TypographyScreen(
    navigator: Navigation3Navigator,
) {
    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = stringResource(R.string.typography),
        navigationIconVisible = true,
        onNavigationClick = { navigator.pop() }
    ) {
        TypographyContent()
    }
}

@Composable
fun TypographyContent() {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        TypographyText(styleName = "displayLarge", style = MaterialTheme.typography.displayLarge)
        TypographyText(styleName = "displayMedium", style = MaterialTheme.typography.displayMedium)
        TypographyText(styleName = "displaySmall", style = MaterialTheme.typography.displaySmall)

        TypographyText(styleName = "headlineLarge", style = MaterialTheme.typography.headlineLarge)
        TypographyText(styleName = "headlineMedium", style = MaterialTheme.typography.headlineMedium)
        TypographyText(styleName = "headlineSmall", style = MaterialTheme.typography.headlineSmall)

        TypographyText(styleName = "titleLarge", style = MaterialTheme.typography.titleLarge)
        TypographyText(styleName = "titleMedium", style = MaterialTheme.typography.titleMedium)
        TypographyText(styleName = "titleSmall", style = MaterialTheme.typography.titleSmall)

        TypographyText(styleName = "labelLarge", style = MaterialTheme.typography.labelLarge)
        TypographyText(styleName = "labelMedium", style = MaterialTheme.typography.labelMedium)
        TypographyText(styleName = "labelSmall", style = MaterialTheme.typography.labelSmall)

        TypographyText(styleName = "bodyLarge", style = MaterialTheme.typography.bodyLarge)
        TypographyText(styleName = "bodyMedium", style = MaterialTheme.typography.bodyMedium)
        TypographyText(styleName = "bodySmall", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun TypographyText(styleName: String, style: TextStyle) {
    Column {
        Text(
            text = "Hello World",
            style = style
        )
        Text(
            text = styleName,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@PreviewDefault
@Composable
private fun Preview() {
    AppTheme {
        TypographyContent()
    }
}
