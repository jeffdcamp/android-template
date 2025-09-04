package org.jdc.template.ux.individualedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.ui.navigation3.ViewModelNavigation3
import org.jdc.template.ui.navigation3.ViewModelNavigation3Impl

@HiltViewModel(assistedFactory = IndividualEditViewModel.Factory::class)
class IndividualEditViewModel
@AssistedInject constructor(
    getIndividualEditUiStateUseCase: GetIndividualEditUiStateUseCase,
    @Assisted individualEditRoute: IndividualEditRoute
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    val uiState: IndividualEditUiState = getIndividualEditUiStateUseCase(individualEditRoute.individualId, viewModelScope) { navigate(it) }

    @AssistedFactory
    interface Factory {
        fun create(individualEditRoute: IndividualEditRoute): IndividualEditViewModel
    }
}
