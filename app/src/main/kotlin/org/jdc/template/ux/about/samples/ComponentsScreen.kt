package org.jdc.template.ux.about.samples

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.jdc.template.ui.navigation.SimpleNavComposeRoute
import org.jdc.template.ux.MainAppScaffoldWithNavBar

object ComponentsRoute : SimpleNavComposeRoute("components")

@Composable
fun ComponentsScreen(
    navController: NavController
) {
    MainAppScaffoldWithNavBar(
        title = "Components",
        onNavigationClick = { navController.popBackStack() }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(Components) { component ->
                ListItem(
                    headlineContent = { Text(component.name) },
                    modifier = Modifier.clickable {
                        navController.navigate(ComponentDetailsRoute.createRoute(component.name))
                    },
                )
            }
        }
    }
}
