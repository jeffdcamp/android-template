@file:Suppress("unused")

package org.jdc.template.inject

import com.vikingsen.inject.viewmodel.ViewModelModule
import dagger.Module

@ViewModelModule
@Module(includes = [ViewModelInject_AssistedModule::class])
abstract class AssistedModule
