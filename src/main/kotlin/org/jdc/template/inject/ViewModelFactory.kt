package org.jdc.template.inject

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory
@Inject constructor(private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>?): T {
        modelClass ?: error("Model class cannot be null")
        var provider = providers[modelClass]
        if (provider == null) {
            provider = providers.filter { (key, value) -> modelClass.isAssignableFrom(key) }
                    .map { it.value }
                    .firstOrNull()
        }
        try {
            @Suppress("UNCHECKED_CAST")
            return provider?.get() as? T ?: error("Unknown model class $modelClass")
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}