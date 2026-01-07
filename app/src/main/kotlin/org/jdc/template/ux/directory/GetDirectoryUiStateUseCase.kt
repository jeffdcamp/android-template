package org.jdc.template.ux.directory

import kotlinx.coroutines.CoroutineScope
import org.jdc.template.shared.model.repository.IndividualRepository
import org.jdc.template.shared.util.ext.stateInDefault
import org.jdc.template.ui.navigation3.Navigation3Action
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.jdc.template.ux.settings.SettingsRoute

class GetDirectoryUiStateUseCase(
    private val individualRepository: IndividualRepository,
) {
    operator fun invoke(
        coroutineScope: CoroutineScope,
        navigate: (Navigation3Action) -> Unit,
    ): DirectoryUiState {
        return DirectoryUiState(
            directoryListFlow = individualRepository.getDirectoryListFlow().stateInDefault(coroutineScope, emptyList()),
            onNewClick = { navigate(Navigation3Action.Navigate(IndividualEditRoute())) },
            onIndividualClick = { individualId -> navigate(Navigation3Action.Navigate(IndividualRoute(individualId))) },
            onSettingsClick = { navigate(Navigation3Action.Navigate(SettingsRoute)) }
        )
    }
}