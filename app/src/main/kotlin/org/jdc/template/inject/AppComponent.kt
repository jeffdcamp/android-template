package org.jdc.template.inject


import android.app.Application
import dagger.Component
import org.jdc.template.App
import org.jdc.template.ux.startup.StartupActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, FragmentBindingModule::class])
interface AppComponent {
    fun inject(application: App)

    // UI
    fun inject(target: InjectorNavHostFragment)
    fun inject(target: StartupActivity)

    // Exported for child-components.
    fun application(): Application
}
