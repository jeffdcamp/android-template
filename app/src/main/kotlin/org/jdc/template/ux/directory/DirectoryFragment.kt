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
import org.jdc.template.ext.collectWhenStarted
import org.jdc.template.ext.getScrollPosition
import org.jdc.template.ext.receiveWhenStarted
import org.jdc.template.ui.menu.CommonMenu
import javax.inject.Inject

@AndroidEntryPoint
class DirectoryFragment : Fragment() {
    @Inject
    lateinit var commonMenu: CommonMenu

    private val viewModel: DirectoryViewModel by viewModels()

    private var _binding: DirectoryFragmentBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.

    private val adapter by lazy { DirectoryAdapter(viewModel) }

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DirectoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = this@DirectoryFragment.viewModel
        binding.lifecycleOwner = this@DirectoryFragment

        setupRecyclerView()

        activity?.setTitle(R.string.app_name)

        savedInstanceState?.let { restoreState(it) }

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewLifecycleOwner.collectWhenStarted(viewModel.directoryListFlow) { list ->
            adapter.submitList(list)
        }

        // Events
        viewLifecycleOwner.receiveWhenStarted(viewModel.eventChannel) { event -> handleEvent(event) }
    }

    private fun handleEvent(event: DirectoryViewModel.Event) {
        when (event) {
            is DirectoryViewModel.Event.NewIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.actionIndividualEditFragment())
            is DirectoryViewModel.Event.ShowIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.actionIndividualFragment(event.individualId))
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
        bundle.putInt("scrollPosition", _binding?.recyclerView?.getScrollPosition() ?: 0)
    }

    private fun restoreState(bundle: Bundle) {
        binding.recyclerView.scrollToPosition(bundle.getInt("scrollPosition", 0))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
