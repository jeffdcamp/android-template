@file:Suppress("unused")

package org.jdc.template.inject

import com.vikingsen.inject.viewmodel.ViewModelModule
import com.vikingsen.inject.worker.WorkerModule
import dagger.Module

@WorkerModule
@ViewModelModule
@Module(includes = [WorkerInject_AssistModule::class, ViewModelInject_AssistModule::class])
abstract class AssistModule
