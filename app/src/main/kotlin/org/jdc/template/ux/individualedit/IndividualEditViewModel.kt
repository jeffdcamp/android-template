package org.jdc.template.ux.individualedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
import org.jdc.template.util.ext.getIndividualId
import javax.inject.Inject

@HiltViewModel
class IndividualEditViewModel
@Inject constructor(
    getIndividualEditUiStateUseCase: GetIndividualEditUiStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    private val individualId = savedStateHandle.getIndividualId(IndividualEditRoute.Arg.INDIVIDUAL_ID)

    val uiState: IndividualEditUiState = getIndividualEditUiStateUseCase(individualId, viewModelScope) { navigate(it) }
}
