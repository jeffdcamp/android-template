package org.jdc.template.ux.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.databinding.DirectoryFragmentBinding
import org.jdc.template.ext.collectWhenStarted
import org.jdc.template.ext.receiveWhenStarted
import org.jdc.template.ui.compose.AppTheme
import org.jdc.template.ui.compose.setContent
import org.jdc.template.ui.fragment.BaseFragment
import org.jdc.template.ui.menu.CommonMenu
import javax.inject.Inject

@AndroidEntryPoint
class DirectoryFragment : Fragment() {
    @Inject
    lateinit var commonMenu: CommonMenu

    private val viewModel: DirectoryViewModel by viewModels()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return setContent {
            DirectoryPage(viewModel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = this@DirectoryFragment.viewModel
        binding.lifecycleOwner = this@DirectoryFragment

        setupRecyclerView()

        activity?.setTitle(R.string.app_name)

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewLifecycleOwner.collectWhenStarted(viewModel.directoryListFlow) { list ->
            adapter.submitList(list)
        }

        // Events
        viewLifecycleOwner.receiveWhenStarted(viewModel.eventChannel) { event ->
            when (event) {
                is DirectoryViewModel.Event.NewIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.actionIndividualEditFragment())
                is DirectoryViewModel.Event.ShowIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.actionIndividualFragment(event.individualId))
            }
        }
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
        if (::binding.isInitialized) { // onSaveInstanceState may be called before onCreateView
            bundle.putInt("scrollPosition", 0)
        }
    }

    private fun restoreState(bundle: Bundle) {
        binding.recyclerView.scrollToPosition(bundle.getInt("scrollPosition", 0))
}

@Composable
private fun DirectoryPage(viewModel: DirectoryViewModel) {
    AppTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = viewModel::addIndividual) {
                    Icon(Icons.Filled.Add)
                }
            }) {
            DirectoryList(viewModel)
        }
    }
}

@Composable
private fun DirectoryList(viewModel: DirectoryViewModel) {
    val directoryList by viewModel.directoryListLiveData.observeAsState(emptyList())
    LazyColumnFor(directoryList) { item ->
        ListItem(modifier = Modifier.clickable {
            viewModel.onDirectoryIndividualClicked(item)
        }) {
            Text(text = item.getFullName())
        }
    }
}
