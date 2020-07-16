package org.jdc.template.ux.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.Composable
import androidx.compose.Recomposer
import androidx.compose.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.livedata.observeAsState
import androidx.ui.material.FloatingActionButton
import androidx.ui.material.ListItem
import androidx.ui.material.Scaffold
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.ui.compose.AppTheme
import org.jdc.template.ui.fragment.BaseFragment
import org.jdc.template.ui.menu.CommonMenu
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class DirectoryFragment : BaseFragment() {
    @Inject
    lateinit var commonMenu: CommonMenu

    private val viewModel: DirectoryViewModel by viewModels()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FrameLayout(requireContext()).apply {
            id = Random.nextInt()
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            setContent(Recomposer.current()) {
                DirectoryPage(viewModel)
            }
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
                    is DirectoryViewModel.Event.NewIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.newIndividual())
                    is DirectoryViewModel.Event.ShowIndividualEvent -> findNavController().navigate(DirectoryFragmentDirections.individual(event.individualId))
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
fun DirectoryPage(viewModel: DirectoryViewModel) {
    val directoryList by viewModel.directoryListLiveData.observeAsState(emptyList())
    AppTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = viewModel::addIndividual) {
                    Icon(Icons.Filled.Add)
                }
            }) {
            DirectoryList(viewModel, directoryList)
        }
    }
}

@Composable
fun DirectoryList(viewModel: DirectoryViewModel, directoryList: List<DirectoryItem>) {
    LazyColumnItems(directoryList) { item ->
        ListItem(onClick = {
            viewModel.onDirectoryIndividualClicked(item)
        }) {
            Text(text = item.getFullName())
        }
    }
}