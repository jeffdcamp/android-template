package org.jdc.template.ux.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.ui.navigation.ViewModelNav
import org.jdc.template.ui.navigation.ViewModelNavImpl
import javax.inject.Inject

@HiltViewModel
class DirectoryViewModel
@Inject constructor(
    getDirectoryUiStateUseCase: GetDirectoryUiStateUseCase,
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {
    val uiState: DirectoryUiState = getDirectoryUiStateUseCase(viewModelScope) { navigate(it) }
}