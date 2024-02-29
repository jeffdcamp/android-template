package org.jdc.template.ux.individualedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
import javax.inject.Inject

@HiltViewModel
class IndividualEditViewModel
@Inject constructor(
    getIndividualEditUiStateUseCase: GetIndividualEditUiStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    private val args = IndividualEditArgs(savedStateHandle)
    val uiState: IndividualEditUiState = getIndividualEditUiStateUseCase(args.individualId, viewModelScope) { navigate(it) }
}
