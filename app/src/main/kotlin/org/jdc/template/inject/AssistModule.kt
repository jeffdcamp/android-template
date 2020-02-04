@file:Suppress("unused")

package org.jdc.template.inject

import androidx.fragment.app.FragmentFactory
import com.vikingsen.inject.fragment.FragmentInjectionFactory
import com.vikingsen.inject.fragment.FragmentModule
import com.vikingsen.inject.viewmodel.ViewModelModule
import com.vikingsen.inject.worker.WorkerModule
import dagger.Binds
import dagger.Module

@FragmentModule
@ViewModelModule
@WorkerModule
@Module(includes = [FragmentInject_AssistModule::class, WorkerInject_AssistModule::class, ViewModelInject_AssistModule::class])
abstract class AssistModule {
    @Binds
    abstract fun bindFragmentFactory(fragmentInjectionFactory: FragmentInjectionFactory): FragmentFactory
}
