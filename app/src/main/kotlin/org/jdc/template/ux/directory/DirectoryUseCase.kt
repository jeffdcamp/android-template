package org.jdc.template.ux.directory

import kotlinx.coroutines.CoroutineScope
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.navigation.NavigationAction
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.jdc.template.ux.settings.SettingsRoute
import javax.inject.Inject

class DirectoryUseCase
@Inject constructor(
    private val individualRepository: IndividualRepository,
) {
    operator fun invoke(
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit,
    ): DirectoryUiState {
        return DirectoryUiState(
            directoryListFlow = individualRepository.getDirectoryListFlow().stateInDefault(coroutineScope, emptyList()),
            onNewClicked = { navigate(NavigationAction.Navigate(IndividualEditRoute.createRoute())) },
            onIndividualClicked = { individualId -> navigate(NavigationAction.Navigate(IndividualRoute.createRoute(individualId))) },
            onSettingsClicked = { navigate(NavigationAction.Navigate(SettingsRoute.createRoute())) }
        )
    }
}