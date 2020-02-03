package org.jdc.template.inject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class InjectorFragmentFactory
@Inject constructor(
        private val creators: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        return creators[fragmentClass]?.get() ?: return createFragmentAsFallback(classLoader, className)
    }

    private fun createFragmentAsFallback(classLoader: ClassLoader, className: String): Fragment {
        Timber.w("No creator found for class: $className. Using default constructor")
        return super.instantiate(classLoader, className)
    }
}