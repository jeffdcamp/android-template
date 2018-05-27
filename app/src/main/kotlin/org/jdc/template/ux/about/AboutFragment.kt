package org.jdc.template.ux.about

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jdc.template.R
import org.jdc.template.databinding.AboutBinding
import org.jdc.template.inject.Injector
import org.jdc.template.ui.fragment.BaseFragment
import javax.inject.Inject

class AboutFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(AboutViewModel::class.java) }
    private lateinit var binding: AboutBinding

    init {
        Injector.get().inject(this)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.about, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = this@AboutFragment.viewModel
            setLifecycleOwner(this@AboutFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.about_title)
        enableActionBarBackArrow(true)
        viewModel.logAnalytics()
    }
}
