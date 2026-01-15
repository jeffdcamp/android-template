package org.jdc.template.inject

import org.jdc.template.analytics.Analytics
import org.jdc.template.analytics.DefaultAnalytics
import org.jdc.template.model.config.RemoteConfig
import org.jdc.template.shared.inject.getSharedKoinModules
import org.jdc.template.startup.AppUpgrade
import org.jdc.template.ux.about.AboutViewModel
import org.jdc.template.ux.acknowledgement.AcknowledgementViewModel
import org.jdc.template.ux.chat.ChatViewModel
import org.jdc.template.ux.chats.ChatsViewModel
import org.jdc.template.ux.directory.DirectoryViewModel
import org.jdc.template.ux.directory.GetDirectoryUiStateUseCase
import org.jdc.template.ux.individual.GetIndividualUiStateUseCase
import org.jdc.template.ux.individual.IndividualViewModel
import org.jdc.template.ux.individualedit.GetIndividualEditUiStateUseCase
import org.jdc.template.ux.individualedit.IndividualEditViewModel
import org.jdc.template.ux.main.MainViewModel
import org.jdc.template.ux.settings.SettingsViewModel
import org.jdc.template.work.RemoteConfigSyncWorker
import org.jdc.template.work.SimpleWorker
import org.jdc.template.work.SyncWorker
import org.jdc.template.work.WorkScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun getAllKoinModules(): List<Module> = buildList {
    add(appModule)
    add(viewModelModule)
    add(workersModule)

    addAll(getSharedKoinModules())
}

val appModule = module {
    singleOf(::RemoteConfig)
    singleOf(::AppUpgrade)
    singleOf(::WorkScheduler)
    single<Analytics> { DefaultAnalytics(androidContext()) }
}

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::AboutViewModel)

    viewModelOf(::DirectoryViewModel)
    singleOf(::GetDirectoryUiStateUseCase)

    viewModelOf(::IndividualViewModel)
    singleOf(::GetIndividualUiStateUseCase)

    viewModelOf(::IndividualEditViewModel)
    singleOf(::GetIndividualEditUiStateUseCase)

    viewModelOf(::SettingsViewModel)
    viewModelOf(::AcknowledgementViewModel)
    viewModelOf(::ChatViewModel)
    viewModelOf(::ChatsViewModel)
}

val workersModule = module {
    workerOf(::SimpleWorker)
    workerOf(::SyncWorker)
    workerOf(::RemoteConfigSyncWorker)
}
