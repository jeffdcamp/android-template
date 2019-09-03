package org.jdc.template.inject


import android.app.Application
import dagger.Component
import org.jdc.template.App
import org.jdc.template.ui.fragment.SettingsFragment
import org.jdc.template.ux.about.AboutFragment
import org.jdc.template.ux.directory.DirectoryFragment
import org.jdc.template.ux.individual.IndividualFragment
import org.jdc.template.ux.individualedit.IndividualEditFragment
import org.jdc.template.ux.startup.StartupActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(application: App)

    // UI
    fun inject(target: StartupActivity)
    fun inject(target: DirectoryFragment)
    fun inject(target: IndividualEditFragment)
    fun inject(target: AboutFragment)
    fun inject(target: IndividualFragment)
    fun inject(target: SettingsFragment)

    // Exported for child-components.
    fun application(): Application
}
