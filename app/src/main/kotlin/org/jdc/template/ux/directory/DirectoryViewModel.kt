package org.jdc.template.ux.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.ui.navigation.ViewModelNavigation
import org.jdc.template.ui.navigation.ViewModelNavigationImpl
import javax.inject.Inject

@HiltViewModel
class DirectoryViewModel
@Inject constructor(
    getDirectoryUiStateUseCase: GetDirectoryUiStateUseCase,
) : ViewModel(), ViewModelNavigation by ViewModelNavigationImpl() {
    val uiState: DirectoryUiState = getDirectoryUiStateUseCase(viewModelScope) { navigate(it) }
}