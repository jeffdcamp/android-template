package org.jdc.template

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jdc.template.model.prefs.Prefs
import timber.log.Timber
import javax.inject.Inject

class AppUpgrade
@Inject constructor(
    private val prefs: Prefs
) {

    fun upgradeApp() = runBlocking(Dispatchers.IO) {
        val lastInstalledVersionCode = prefs.lastInstalledVersionCode
        Timber.i("Checking for app upgrade from [%d]", lastInstalledVersionCode)

        if (lastInstalledVersionCode == 0) {
            Timber.i("Skipping app upgrade on fresh install")
            prefs.lastInstalledVersionCode = BuildConfig.VERSION_CODE
            return@runBlocking
        }

//        if (lastInstalledVersionCode < VERSION_CODE_HERE) {
//            migrateXXX()
//        }

        // set the current version
        prefs.lastInstalledVersionCode = BuildConfig.VERSION_CODE
    }
}
