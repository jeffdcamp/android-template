package org.jdc.template.ux.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.jdc.template.ux.settings.SettingsRoute
import javax.inject.Inject

@HiltViewModel
class DirectoryViewModel
@Inject constructor(
    individualRepository: IndividualRepository
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    val uiState: DirectoryUiState = DirectoryUiState(
        directoryListFlow = individualRepository.getDirectoryListFlow().stateInDefault(viewModelScope, emptyList()),

        onNewClicked = { navigate(IndividualEditRoute.createRoute()) },
        onIndividualClicked = { individualId -> navigate(IndividualRoute.createRoute(IndividualId(individualId))) },

        onSettingsClicked = { navigate(SettingsRoute.createRoute()) }
    )
}