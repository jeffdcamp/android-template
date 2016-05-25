package org.jdc.template.inject


import android.app.Application
import dagger.Component
import org.jdc.template.App
import org.jdc.template.ui.activity.*
import org.jdc.template.ui.adapter.DirectoryAdapter
import org.jdc.template.ui.fragment.SettingsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    // UI
    fun inject(application: App)


    fun inject(target: StartupActivity)
    fun inject(target: DirectoryActivity)
    fun inject(target: IndividualEditActivity)
    fun inject(target: SettingsActivity)
    fun inject(target: AboutActivity)
    fun inject(target: IndividualActivity)

    // Adapters
    fun inject(target: DirectoryAdapter)

    // Exported for child-components.
    fun application(): Application

    fun inject(target: SettingsFragment)
}
