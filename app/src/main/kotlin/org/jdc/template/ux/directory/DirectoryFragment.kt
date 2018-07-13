package org.jdc.template.ux.directory

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Int
import org.jdc.template.R
import org.jdc.template.databinding.DirectoryBinding
import org.jdc.template.ext.getScrollPosition
import org.jdc.template.inject.Injector
import org.jdc.template.ui.fragment.BaseFragment
import org.jdc.template.ui.menu.CommonMenu
import javax.inject.Inject


class DirectoryFragment : BaseFragment() {
    @Inject
    lateinit var commonMenu: CommonMenu
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DirectoryViewModel::class.java) }
    private lateinit var binding: DirectoryBinding
    private val adapter by lazy { DirectoryAdapter(viewModel) }

    init {
        Injector.get().inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.directory, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = this@DirectoryFragment.viewModel
            setLifecycleOwner(this@DirectoryFragment)
        }

        setupRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.setTitle(R.string.app_name)

        enableActionBarBackArrow(false)

        savedInstanceState?.let { restoreState(it) }

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        // Events
        viewModel.onNewIndividualEvent.observe {
            showNewIndividual()
        }
        viewModel.showIndividualEvent.observeNotNull {
            showIndividual(it)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.directory_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return commonMenu.onOptionsItemSelected(findNavController(), item) || super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        with(SaveStateOptions) {
            bundle.scrollPosition = binding.recyclerView.getScrollPosition()
        }
    }

    private fun showNewIndividual() {
        findNavController().navigate(DirectoryFragmentDirections.newIndividual())
    }

    private fun showIndividual(individualId: Long) {
        val directions = DirectoryFragmentDirections.showIndividual(individualId)
        findNavController().navigate(directions)
    }

    private fun restoreState(bundle: Bundle) {
        with(SaveStateOptions) {
            binding.recyclerView.scrollToPosition(bundle.scrollPosition!!)
        }
    }

    object SaveStateOptions {
        var Bundle.scrollPosition by BundleExtra.Int()
    }
}
