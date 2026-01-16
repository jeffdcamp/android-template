package org.jdc.template.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.verify.assertTrue
import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.CoroutineDispatcher
import org.jdc.template.di.getAllKoinModules
import org.jdc.template.ux.chat.ChatRoute
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.verify.verify
import kotlin.reflect.KClass
import kotlin.test.Test

class AppKoinModuleCheck {

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

    @Test
    fun `verify no duplicate definitions in Koin modules`() {
        koinApplication {
            allowOverride(false) // STRICT MODE: Fail on duplicates
            modules(getAllKoinModules())
        }
    }

    @Test
    fun `verify all CoroutineWorker are registered in Koin`() {
        findMissingRegisteredTypes(CoroutineWorker::class)
    }

    @OptIn(KoinInternalApi::class)
    @Test
    fun `verify all ViewModels are registered in Koin`() {
        findMissingRegisteredTypes(ViewModel::class)
    }

    private fun findMissingRegisteredTypes(baseClass: KClass<*>) {
        // 1. Pre-fetch all files that look like Koin Modules
        val koinModules = getAllKoinModules()

        val koinDefinitions: List<String> = getRegisteredClassNames(koinModules)
        // koinDefinitions.forEach { println("def: $it") }

        // 2. Run the check
        Konsist.scopeFromProject()
            .classes()
            .withAllParentsOf(baseClass, indirectParents = true)
            .assertTrue { classDeclaration ->
//                println("+++ ${classDeclaration.name}")
                val isRegistered = koinDefinitions.any { it == classDeclaration.name }

                if (!isRegistered) {
                    println("FAILED: ${classDeclaration.name} is not registered in any Koin module.")
                }
                isRegistered
            }
    }

    @OptIn(KoinInternalApi::class)
    fun getRegisteredClassNames(modules: List<Module>): List<String> {
        return modules
            .flatMap { module ->
                module.mappings.values // Get all BeanDefinitions
            }
            .map { instanceFactory : InstanceFactory<*> ->
                // Extract the KClass simple name
                instanceFactory.beanDefinition.primaryType.simpleName.orEmpty()
            }
            .distinct() // Remove duplicates if multiple modules define the same type
    }
}
