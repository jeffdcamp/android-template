package org.jdc.template.ux.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import org.jdc.template.shared.model.db.main.directoryitem.DirectoryItemEntityView
import org.jdc.template.shared.model.domain.inline.IndividualId
import org.jdc.template.shared.model.repository.IndividualRepository
import org.jdc.template.shared.util.ext.stateInDefault
import org.jdc.template.ui.navigation3.ViewModelNavigation3
import org.jdc.template.ui.navigation3.ViewModelNavigation3Impl
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.jdc.template.ux.settings.SettingsRoute

class DirectoryViewModel(
    individualRepository: IndividualRepository
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    val uiStateFlow: StateFlow<DirectoryUiState> = individualRepository.getDirectoryListFlow().map { list: List<DirectoryItemEntityView> ->
        if (list.isEmpty()) {
            DirectoryUiState.Empty
        } else {
            DirectoryUiState.Ready(list)
        }
    }.stateInDefault(viewModelScope, DirectoryUiState.Loading)

    fun onNewClick() {
        navigate(IndividualEditRoute())
    }

    fun onIndividualClick(individualId: IndividualId) {
        navigate(IndividualRoute(individualId))
    }

    fun onSettingsClick() {
        navigate(SettingsRoute)
    }
}

sealed interface DirectoryUiState {
    data object Loading : DirectoryUiState

    data class Ready(
        val directoryList: List<DirectoryItemEntityView>,
    ) : DirectoryUiState

    data object Empty : DirectoryUiState
}
