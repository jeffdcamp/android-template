package org.jdc.template.inject


import android.app.Application
import dagger.Component
import org.jdc.template.App
import org.jdc.template.ui.activity.SettingsActivity
import org.jdc.template.ui.fragment.SettingsFragment
import org.jdc.template.ux.about.AboutActivity
import org.jdc.template.ux.directory.DirectoryActivity
import org.jdc.template.ux.individual.IndividualActivity
import org.jdc.template.ux.individualedit.IndividualEditActivity
import org.jdc.template.ux.startup.StartupActivity
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
    fun inject(target: SettingsFragment)

    // Exported for child-components.
    fun application(): Application

}
