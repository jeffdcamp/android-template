package org.jdc.template.inject

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import javax.inject.Inject

class InjectorNavHostFragment : NavHostFragment() {

    @Inject
    protected lateinit var fragmentFactory: FragmentFactory

    override fun onAttach(context: Context) {
        Injector.get().inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
    }
}