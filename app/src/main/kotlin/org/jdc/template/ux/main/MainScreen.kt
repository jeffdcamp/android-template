package org.jdc.template.ux.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.serialization.NavKeySerializer
import androidx.navigation3.ui.NavDisplay
import org.jdc.template.ui.navigation.NavigationState
import org.jdc.template.ui.navigation.navigator.TopLevelBackStackNavigator
import org.jdc.template.ui.navigation.rememberNavigationState
import org.jdc.template.ui.navigation.toEntries
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.about.AboutScreen
import org.jdc.template.ux.about.typography.TypographyRoute
import org.jdc.template.ux.about.typography.TypographyScreen
import org.jdc.template.ux.acknowledgement.AcknowledgementScreen
import org.jdc.template.ux.acknowledgement.AcknowledgmentsRoute
import org.jdc.template.ux.chat.ChatRoute
import org.jdc.template.ux.chat.ChatScreen
import org.jdc.template.ux.chat.ChatViewModel
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
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen() {
    val navigationState: NavigationState = rememberNavigationState(
        startRoute = DirectoryRoute,
        topLevelRoutes = NavBarItem.entries.map { it.route }.toSet(),
        navKeySerializer = NavKeySerializer()
    )

    val navigator = remember { TopLevelBackStackNavigator(navigationState) }

    val entryProvider: (NavKey) -> NavEntry<NavKey> = entryProvider {
        entry<DirectoryRoute> { DirectoryScreen(navigator, koinViewModel()) }
        entry<IndividualRoute> { key -> IndividualScreen(navigator, koinViewModel<IndividualViewModel> { parametersOf(key) }) }
        entry<IndividualEditRoute> { key -> IndividualEditScreen(navigator,  koinViewModel<IndividualEditViewModel> { parametersOf(key) }) }
        entry<ChatsRoute> { ChatsScreen(navigator,  koinViewModel()) }
        entry<ChatRoute> { key ->  ChatScreen(navigator, koinViewModel<ChatViewModel> { parametersOf(key) }) }
        entry<SettingsRoute> { SettingsScreen(navigator, koinViewModel()) }
        entry<AboutRoute> { AboutScreen(navigator, koinViewModel()) }
        entry<TypographyRoute> { TypographyScreen(navigator) }
        entry<AcknowledgmentsRoute> { AcknowledgementScreen(navigator, koinViewModel()) }
    }

    val decorators: List<NavEntryDecorator<NavKey>> = listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator()
    )

    NavDisplay(
        entries = navigationState.toEntries(entryProvider, decorators),
        onBack = { navigator.pop() }
    )
}
