package org.jdc.template.ux

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.jdc.template.inject.ViewModelFactory
import org.jdc.template.inject.ViewModelKey
import org.jdc.template.ux.directory.DirectoryViewModel

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
    
    @Binds
    @IntoMap
    @ViewModelKey(DirectoryViewModel::class)
    internal abstract fun bindDirectoryViewModel(viewModel: DirectoryViewModel): ViewModel
}

