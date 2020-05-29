package org.jdc.template.ux.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikingsen.inject.fragment.FragmentInject
import com.vikingsen.inject.viewmodel.savedstate.SavedStateViewModelFactory
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Int
import org.jdc.template.R
import org.jdc.template.databinding.DirectoryFragmentBinding
import org.jdc.template.ext.getScrollPosition
import org.jdc.template.ui.fragment.BaseFragment
import org.jdc.template.ui.menu.CommonMenu


class DirectoryFragment
@FragmentInject constructor(
    private val commonMenu: CommonMenu,
    private val viewModelFactoryFactory: SavedStateViewModelFactory.Factory
) : BaseFragment() {
    private val viewModel: DirectoryViewModel by viewModels { viewModelFactoryFactory.create(this, null) }
    private lateinit var binding: DirectoryFragmentBinding
    private val adapter by lazy { DirectoryAdapter(viewModel) }

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.directory_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = this@DirectoryFragment.viewModel
        binding.lifecycleOwner = this@DirectoryFragment

        setupRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.setTitle(R.string.app_name)

        savedInstanceState?.let { restoreState(it) }

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewModel.directoryListLiveData.observeKt { list ->
            adapter.submitList(list)
        }

        // Events
        lifecycleScope.launchWhenStarted {
            for (event in viewModel.eventChannel) {
                when (event) {
                    is DirectoryViewModel.Event.NewIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.newIndividual())
                    is DirectoryViewModel.Event.ShowIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.individual(event.individualId))
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.directory_menu, menu)
        inflater.inflate(R.menu.common_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return commonMenu.onOptionsItemSelected(findNavController(), item) || super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        with(SaveStateOptions) {
            if (::binding.isInitialized) { // onSaveInstanceState may be called before onCreateView
                bundle.scrollPosition = binding.recyclerView.getScrollPosition()
            }
        }
    }

    private fun restoreState(bundle: Bundle) {
        with(SaveStateOptions) {
            binding.recyclerView.scrollToPosition(bundle.scrollPosition ?: 0)
        }
    }

    object SaveStateOptions {
        var Bundle.scrollPosition by BundleExtra.Int()
    }
}
