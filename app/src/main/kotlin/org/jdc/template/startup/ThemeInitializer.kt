package org.jdc.template.startup

import android.content.Context
import androidx.startup.Initializer
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import org.jdc.template.coroutine.ProcessScope
import org.jdc.template.ui.ThemeManager

class ThemeInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val applicationContext = checkNotNull(context.applicationContext) { "Missing Application Context" }
        val injector = EntryPoints.get(applicationContext, ThemeInitializerInjector::class.java)

        ProcessScope.launch {
            injector.getThemeManager().applyTheme()
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ThemeInitializerInjector {
        fun getThemeManager(): ThemeManager
    }
}