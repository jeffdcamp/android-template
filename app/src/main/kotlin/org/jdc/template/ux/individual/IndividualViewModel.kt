package org.jdc.template.ux.individual

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.ui.navigation.ViewModelNavigation
import org.jdc.template.ui.navigation.ViewModelNavigationImpl
import javax.inject.Inject

@HiltViewModel
class IndividualViewModel
@Inject constructor(
    getIndividualUiStateUseCase: GetIndividualUiStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNavigation by ViewModelNavigationImpl() {
    private val individualRoute = savedStateHandle.toRoute<IndividualRoute>(IndividualRoute.typeMap())
    val uiState: IndividualUiState = getIndividualUiStateUseCase(individualRoute.individualId, viewModelScope) { navigate(it) }
}
