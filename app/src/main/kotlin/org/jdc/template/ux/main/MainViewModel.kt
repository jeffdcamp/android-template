package org.jdc.template.ux.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jdc.template.domain.individual.CreateIndividualTestDataUseCase
import org.jdc.template.model.repository.SettingsRepository
import org.jdc.template.ui.navigation.DefaultNavBarConfig
import org.jdc.template.ui.navigation.NavBarConfig
import org.jdc.template.ui.navigation.ViewModelNavBar
import org.jdc.template.ui.navigation.ViewModelNavBarImpl
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.directory.DirectoryRoute
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    settingsRepository: SettingsRepository,
    private val createIndividualTestDataUseCase: CreateIndividualTestDataUseCase
) : ViewModel(), ViewModelNavBar<NavBarItem> by ViewModelNavBarImpl(NavBarItem.PEOPLE, createNavBarConfig()) {
    val uiState = MainUiState(
        themeFlow = settingsRepository.themeFlow.stateInDefault(viewModelScope, null)
    )

    @VisibleForTesting
    suspend fun createSampleData() {
        createIndividualTestDataUseCase()
    }

    companion object {
        fun createNavBarConfig(): NavBarConfig<NavBarItem>  {
            return DefaultNavBarConfig(
                mapOf(
                    NavBarItem.PEOPLE to DirectoryRoute.createRoute(),
                    NavBarItem.ABOUT to AboutRoute.createRoute()
                )
            )
        }
    }
}