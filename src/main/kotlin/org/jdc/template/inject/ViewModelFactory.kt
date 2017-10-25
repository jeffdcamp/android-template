package org.jdc.template.inject

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory
@Inject constructor(private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var provider = providers[modelClass]
        if (provider == null) {
            provider = providers.filter { (key, _) -> modelClass.isAssignableFrom(key) }
                    .map { it.value }
                    .firstOrNull()
        }
        try {
            @Suppress("UNCHECKED_CAST")
            return provider?.get() as? T ?: error("Unknown model class $modelClass:  Make $modelClass has a Provider in your Dagger ViewModel module")
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}