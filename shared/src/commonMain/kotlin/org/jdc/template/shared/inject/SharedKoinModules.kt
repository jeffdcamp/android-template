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
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

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

interface CoroutineDispatchers {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}

val coroutineModule = module {
    // ===== Scopes =====
    // ApplicationScope (No name on this one so that it IS the default one that is used if there is no name is provided)
    single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.Default) }

    // ===== Dispatchers =====
    // IoDispatcher (No name on this one so that it IS the default one that is used if there is no name is provided)
    single<CoroutineDispatchers> {
        object : CoroutineDispatchers {
            override val default = Dispatchers.Default
            override val io = Dispatchers.IO
            override val main = Dispatchers.Main
        }
    }
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
    factoryOf(::CreateIndividualTestDataUseCase)
    factoryOf(::CreateIndividualLargeTestDataUseCase)
}

val repositoryModule = module {
    singleOf(::SettingsRepository)
    singleOf(::IndividualRepository)
    singleOf(::ChatRepository)
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
