package org.jdc.template.ux.individualedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.ui.navigation.ViewModelNavigation
import org.jdc.template.ui.navigation.ViewModelNavigationImpl
import javax.inject.Inject

@HiltViewModel
class IndividualEditViewModel
@Inject constructor(
    getIndividualEditUiStateUseCase: GetIndividualEditUiStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNavigation by ViewModelNavigationImpl() {
    private val individualEditRoute = savedStateHandle.toRoute<IndividualEditRoute>(IndividualEditRoute.typeMap())
    val uiState: IndividualEditUiState = getIndividualEditUiStateUseCase(individualEditRoute.individualId, viewModelScope) { navigate(it) }
}
