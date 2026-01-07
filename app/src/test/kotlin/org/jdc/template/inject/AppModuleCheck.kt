package org.jdc.template.inject

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.work.WorkerParameters
import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.CoroutineDispatcher
import org.jdc.template.ux.chat.ChatRoute
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.verify.verify
import kotlin.test.Test

class AppModuleCheck {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkKoinModule() {
        val extraTypes = listOf(
            Context::class,
            Application::class,

            DataStore::class, // Implementations of DataStore is done in the single<UserDataStore>
            CoroutineDispatcher::class, // Implementations of CoroutineDispatcher is done in the single<AppCoroutineDispatchers>
            WorkerParameters::class, // Provided with Koin workerOf { }

            HttpClientEngine::class, // Provided by expect/actual

            // Route items that are supplied by koinViewModel<XxxViewModel> { parametersOf(key) }
            IndividualRoute::class,
            IndividualEditRoute::class,
            ChatRoute::class
        )

        // Combine all modules into one "wrapper" module for verification.
        // This ensures all modules will know about definitions provided by other modules (Example: appModule can see definitions in datastoreModule)
        module {
            includes(getAllKoinModules())
        }.verify(extraTypes)
    }
}