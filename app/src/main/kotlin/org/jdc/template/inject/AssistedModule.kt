@file:Suppress("unused")

package org.jdc.template.inject

import com.vikingsen.inject.worker.WorkerModule
import com.vikingsen.inject.viewmodel.ViewModelModule
import dagger.Module

@WorkerModule
@ViewModelModule
@Module(includes = [WorkerInject_AssistedModule::class, ViewModelInject_AssistedModule::class])
abstract class AssistedModule
