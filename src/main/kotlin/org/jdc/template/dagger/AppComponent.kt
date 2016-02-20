package org.jdc.template.dagger


import android.app.Application
import dagger.Component
import org.jdc.template.App
import org.jdc.template.ui.*
import org.jdc.template.ui.adapter.DirectoryAdapter
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    // UI
    fun inject(application: App)


    fun inject(target: StartupActivity)
    fun inject(target: DirectoryActivity)
    fun inject(target: DirectoryFragment)
    fun inject(target: IndividualFragment)
    fun inject(target: IndividualEditActivity)
    fun inject(target: IndividualEditFragment)
    fun inject(target: SettingsActivity)
    fun inject(target: AboutActivity)
    fun inject(target: IndividualActivity)

    // Adapters
    fun inject(target: DirectoryAdapter)

    // Exported for child-components.
    fun application(): Application

    fun inject(target: SettingsFragment)
}
