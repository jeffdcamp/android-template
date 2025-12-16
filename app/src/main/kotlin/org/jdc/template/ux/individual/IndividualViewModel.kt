package org.jdc.template.ux.individual

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.ui.navigation3.ViewModelNavigation3
import org.jdc.template.ui.navigation3.ViewModelNavigation3Impl

@HiltViewModel(assistedFactory = IndividualViewModel.Factory::class)
class IndividualViewModel
@AssistedInject constructor(
    getIndividualUiStateUseCase: GetIndividualUiStateUseCase,
    @Assisted individualRoute: IndividualRoute,
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    val uiState: IndividualUiState = getIndividualUiStateUseCase(individualRoute.individualId, viewModelScope) { navigate(it) }

    @AssistedFactory
    interface Factory {
        fun create(individualRoute: IndividualRoute): IndividualViewModel
    }

    companion object {
        @Composable
        fun create(individualRoute: IndividualRoute) = hiltViewModel<IndividualViewModel, IndividualViewModel.Factory>(creationCallback = { it.create(individualRoute) })
    }
}
