package org.jdc.template.startup

import co.touchlab.kermit.Logger
import kotlinx.coroutines.runBlocking
import org.jdc.template.BuildConfig
import org.jdc.template.shared.inject.CoroutineDispatchers
import org.jdc.template.shared.model.repository.SettingsRepository

class AppUpgrade(
    private val settingsRepository: SettingsRepository,
    private val dispatchers: CoroutineDispatchers
) {

    fun upgradeApp() = runBlocking(dispatchers.io) {
        val lastInstalledVersionCode = settingsRepository.getLastInstalledVersionCode()
        Logger.i { "Checking for app upgrade from [$lastInstalledVersionCode]" }

        if (lastInstalledVersionCode == 0) {
            Logger.i { "Skipping app upgrade on fresh install" }
            settingsRepository.setLastInstalledVersionCodeAsync(BuildConfig.VERSION_CODE)
            return@runBlocking
        }

//        if (lastInstalledVersionCode < VERSION_CODE_HERE) {
//            migrateXXX()
//        }

        // set the current version
        settingsRepository.setLastInstalledVersionCodeAsync(BuildConfig.VERSION_CODE)
    }
}
