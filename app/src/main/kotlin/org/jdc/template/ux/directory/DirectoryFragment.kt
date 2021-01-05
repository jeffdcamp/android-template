package org.jdc.template.ux.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.databinding.DirectoryFragmentBinding
import org.jdc.template.ext.autoCleared
import org.jdc.template.ext.getScrollPosition
import org.jdc.template.ext.withLifecycleOwner
import org.jdc.template.ui.menu.CommonMenu
import javax.inject.Inject

@AndroidEntryPoint
class DirectoryFragment : Fragment() {
    @Inject
    lateinit var commonMenu: CommonMenu

    private val viewModel: DirectoryViewModel by viewModels()
    private var binding: DirectoryFragmentBinding by autoCleared()
    private var adapter: DirectoryAdapter by autoCleared()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DirectoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = this@DirectoryFragment.viewModel
        binding.lifecycleOwner = this@DirectoryFragment

        setupRecyclerView()

        activity?.setTitle(R.string.app_name)

        savedInstanceState?.let { restoreState(it) }

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        withLifecycleOwner(this) {
            viewModel.directoryListFlow.collectWhenStarted { list ->
                adapter.submitList(list)
            }

            // Events
            viewModel.eventChannel.receiveWhenStarted { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: DirectoryViewModel.Event) {
        when (event) {
            is DirectoryViewModel.Event.NewIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.actionToIndividualEditFragment())
            is DirectoryViewModel.Event.ShowIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.actionToIndividualFragment(event.individualId))
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = DirectoryAdapter(viewModel)
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
        bundle.putInt("scrollPosition", binding.recyclerView.getScrollPosition())
    }

    private fun restoreState(bundle: Bundle) {
        binding.recyclerView.scrollToPosition(bundle.getInt("scrollPosition", 0))
    }
}
