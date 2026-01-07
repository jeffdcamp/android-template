package org.jdc.template.ux.individual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.jdc.template.ui.navigation3.ViewModelNavigation3
import org.jdc.template.ui.navigation3.ViewModelNavigation3Impl

class IndividualViewModel(
    getIndividualUiStateUseCase: GetIndividualUiStateUseCase,
    individualRoute: IndividualRoute,
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    val uiState: IndividualUiState = getIndividualUiStateUseCase(individualRoute.individualId, viewModelScope) { navigate(it) }
}
