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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.ext.receiveWhenStarted
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.ui.compose.AppTheme
import org.jdc.template.ui.compose.setContent
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
        activity?.setTitle(R.string.app_name)

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
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
            val directoryList = viewModel.directoryListFlow.collectAsState(emptyList())
            DirectoryList(directoryList.value, viewModel::onDirectoryIndividualClicked)
        }
    }
}

@Composable
private fun DirectoryList(directoryList: List<DirectoryItem>, onClick: (DirectoryItem) -> Unit = {}) {
    LazyColumnFor(directoryList) { item ->
        ListItem(modifier = Modifier.clickable {
            onClick(item)
        }) {
            Text(text = item.getFullName())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDirectory() {
    val directoryList = listOf(
        DirectoryItem(0L, "Test 1", "Person"),
        DirectoryItem(1L, "Test 2", "Person"),
    )
    DirectoryList(directoryList)
}
