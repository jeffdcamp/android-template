package org.jdc.template.startup

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.jdc.template.work.WorkScheduler

class WorkManagerInitializer : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        val applicationContext = checkNotNull(context.applicationContext) { "Missing Application Context" }
        val injector = EntryPoints.get(applicationContext, WorkManagerInitializerInjector::class.java)

        val configuration = Configuration.Builder()
            .setWorkerFactory(injector.workerFactory)
            .build()

        WorkManager.initialize(context, configuration)

        val workManager = WorkManager.getInstance(context)

        // schedule all periodic work
        injector.workScheduler.startPeriodicWorkSchedules()

        return workManager
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(TimberInitializer::class.java, RemoteConfigInitializer::class.java)
    }

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface WorkManagerInitializerInjector {
        val workerFactory: HiltWorkerFactory
        val workScheduler: WorkScheduler
    }
}