package org.jdc.template.ux.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.ui.navigation3.ViewModelNavigation3
import org.jdc.template.ui.navigation3.ViewModelNavigation3Impl
import javax.inject.Inject

@HiltViewModel
class DirectoryViewModel
@Inject constructor(
    getDirectoryUiStateUseCase: GetDirectoryUiStateUseCase,
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    val uiState: DirectoryUiState = getDirectoryUiStateUseCase(viewModelScope) { navigate(it) }
}