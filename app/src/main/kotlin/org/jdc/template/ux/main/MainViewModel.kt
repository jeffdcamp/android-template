package org.jdc.template.ux.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jdc.template.model.data.DisplayThemeType
import org.jdc.template.model.repository.IndividualRepository
import org.jdc.template.model.repository.SettingsRepository
import org.jdc.template.ui.navigation.DefaultNavBarConfig
import org.jdc.template.ui.navigation.NavBarConfig
import org.jdc.template.ui.navigation.ViewModelNavBar
import org.jdc.template.ui.navigation.ViewModelNavBarImpl
import org.jdc.template.util.ext.stateInDefault
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.directory.DirectoryRoute
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    settingsRepository: SettingsRepository,
    private val individualRepository: IndividualRepository
) : ViewModel(), ViewModelNavBar<NavBarItem> by ViewModelNavBarImpl(NavBarItem.PEOPLE, createNavBarConfig()) {
    val themeFlow: StateFlow<DisplayThemeType?> = settingsRepository.themeFlow.stateInDefault(viewModelScope, null)

    var isReady: Boolean = false
        private set

    fun startup() = viewModelScope.launch {
        // run any startup/initialization code here (NOTE: these tasks should NOT exceed 1000ms (per Google Guidelines))
        Timber.i("Startup task...")

        // Startup finished
        isReady = true
        Timber.i("Startup finished")
    }

    @VisibleForTesting
    suspend fun createSampleData() {
        individualRepository.createSampleData()
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