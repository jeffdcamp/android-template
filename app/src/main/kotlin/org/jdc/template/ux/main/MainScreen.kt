package org.jdc.template.ux.main

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.serialization.NavKeySerializer
import androidx.navigation3.ui.NavDisplay
import org.jdc.template.ui.navigation3.navigator.TopLevelBackStackNavigator
import org.jdc.template.ui.navigation3.rememberNavigationState
import org.jdc.template.ui.navigation3.toEntries
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.about.AboutScreen
import org.jdc.template.ux.about.typography.TypographyRoute
import org.jdc.template.ux.about.typography.TypographyScreen
import org.jdc.template.ux.acknowledgement.AcknowledgementScreen
import org.jdc.template.ux.acknowledgement.AcknowledgmentsRoute
import org.jdc.template.ux.chat.ChatRoute
import org.jdc.template.ux.chat.ChatScreen
import org.jdc.template.ux.chats.ChatsRoute
import org.jdc.template.ux.chats.ChatsScreen
import org.jdc.template.ux.directory.DirectoryRoute
import org.jdc.template.ux.directory.DirectoryScreen
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individual.IndividualScreen
import org.jdc.template.ux.individual.IndividualViewModel
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.jdc.template.ux.individualedit.IndividualEditScreen
import org.jdc.template.ux.individualedit.IndividualEditViewModel
import org.jdc.template.ux.settings.SettingsRoute
import org.jdc.template.ux.settings.SettingsScreen

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState(
        startRoute = DirectoryRoute,
        topLevelRoutes = NavBarItem.entries.map { it.route }.toSet(),
        navKeySerializer = NavKeySerializer()
    )

    val navigator = TopLevelBackStackNavigator(navigationState)

    val entryProvider: (NavKey) -> NavEntry<NavKey> = entryProvider {
        entry<DirectoryRoute> { DirectoryScreen(navigator, hiltViewModel()) }
        entry<IndividualRoute> { key ->
            IndividualScreen(navigator, hiltViewModel<IndividualViewModel, IndividualViewModel.Factory>(creationCallback = { it.create(key) }))
        }
        entry<IndividualEditRoute> { key ->
            IndividualEditScreen(navigator, hiltViewModel<IndividualEditViewModel, IndividualEditViewModel.Factory>(creationCallback = { it.create(key) }))
        }
        entry<ChatsRoute> { ChatsScreen(navigator, hiltViewModel()) }
        entry<ChatRoute> { ChatScreen(navigator, hiltViewModel()) }
        entry<SettingsRoute> { SettingsScreen(navigator, hiltViewModel()) }
        entry<AboutRoute> { AboutScreen(navigator, hiltViewModel()) }
        entry<TypographyRoute> { TypographyScreen(navigator) }
        entry<AcknowledgmentsRoute> { AcknowledgementScreen(navigator, hiltViewModel()) }
    }

    NavDisplay(
        entries = navigationState.toEntries(entryProvider),
        onBack = { navigator.pop() }
    )
}
