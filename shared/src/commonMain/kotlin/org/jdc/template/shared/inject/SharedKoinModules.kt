package org.jdc.template.shared.inject

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import okio.FileSystem
import okio.SYSTEM
import org.jdc.template.shared.domain.usecase.CreateIndividualLargeTestDataUseCase
import org.jdc.template.shared.domain.usecase.CreateIndividualTestDataUseCase
import org.jdc.template.shared.httpClientEngine
import org.jdc.template.shared.model.datastore.DevicePreferenceDataSource
import org.jdc.template.shared.model.datastore.UserPreferenceDataSource
import org.jdc.template.shared.model.db.main.MainDatabase
import org.jdc.template.shared.model.repository.ChatRepository
import org.jdc.template.shared.model.repository.IndividualRepository
import org.jdc.template.shared.model.repository.SettingsRepository
import org.jdc.template.shared.model.webservice.KtorClientDefaults.defaultSetup
import org.jdc.template.shared.model.webservice.ResponseTimePlugin
import org.jdc.template.shared.model.webservice.colors.ColorService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val DefaultDispatcher = named("DefaultDispatcher")
val IoDispatcher = named("IoDispatcher")
val ApplicationScope = named("ApplicationScope")

fun getSharedKoinModules(): List<Module> = listOf(
    fileSystemModule,
    coroutineModule,
    databaseBuilderModule,
    databaseModule,
    datastoreModule,
    datastorePreferenceModule,
    repositoryModule,
    serviceModule,
    useCaseModule
)

val coroutineModule = module {
    single<CoroutineDispatcher> (DefaultDispatcher) { Dispatchers.Default }
    single<CoroutineDispatcher> (IoDispatcher) { Dispatchers.IO }
    single<CoroutineScope>(ApplicationScope) { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
}

expect val databaseBuilderModule: Module

val databaseModule = module {
    single<MainDatabase> { MainDatabase.getDatabase(get()) }
}

expect val datastoreModule: Module

val fileSystemModule = module {
    single { FileSystem.SYSTEM }
}

val useCaseModule = module {
    factory { CreateIndividualTestDataUseCase(get(), get(IoDispatcher)) }
    factory { CreateIndividualLargeTestDataUseCase(get(), get(IoDispatcher)) }
}

val repositoryModule = module {
    single { SettingsRepository(get(), get(), get(ApplicationScope)) }
    singleOf(::IndividualRepository)
    single { ChatRepository(get(), get(IoDispatcher), get(ApplicationScope)) }
}

val datastorePreferenceModule = module {
    singleOf(::UserPreferenceDataSource)
    singleOf(::DevicePreferenceDataSource)
}

val serviceModule = module {
    single {
        HttpClient(httpClientEngine()) {
            install(Logging) {
                defaultSetup()
            }
            install(ResponseTimePlugin)
            install(Resources)
            install(ContentNegotiation) {
                defaultSetup(allowAnyContentType = true)
            }

            defaultRequest {
                url("https://raw.githubusercontent.com/jeffdcamp/android-template/33017aa38f59b3ff728a26c1ee350e58c8bb9647/src/test/")
            }
        }
    }

    singleOf(::ColorService)
}
