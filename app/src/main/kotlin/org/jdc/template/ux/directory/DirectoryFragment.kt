package org.jdc.template.ux.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.ui.compose.AppTheme
import org.jdc.template.ui.compose.setContent
import org.jdc.template.ui.fragment.BaseFragment
import org.jdc.template.ui.menu.CommonMenu
import javax.inject.Inject

@AndroidEntryPoint
class DirectoryFragment : BaseFragment() {
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
        super.onViewCreated(view, savedInstanceState)

        activity?.setTitle(R.string.app_name)

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {

        // Events
        lifecycleScope.launchWhenStarted {
            for (event in viewModel.eventChannel) {
                when (event) {
                    is DirectoryViewModel.Event.NewIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.actionIndividualEditFragment())
                    is DirectoryViewModel.Event.ShowIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.actionIndividualFragment(event.individualId))
                }
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
