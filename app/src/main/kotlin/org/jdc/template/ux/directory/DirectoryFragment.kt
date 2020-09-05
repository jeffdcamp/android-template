package org.jdc.template.ux.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.viewinterop.viewModel
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.ui.compose.AppTheme
import org.jdc.template.ui.compose.setContent
import org.jdc.template.ui.menu.CommonMenu
import org.jdc.template.ux.main.NavControllerAmbient

@AndroidEntryPoint
class DirectoryFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return setContent {
            Providers(NavControllerAmbient provides findNavController()) {
                DirectoryPage()
            }
        }
    }
}

@Composable
private fun DirectoryPage() {
    AppTheme {
        val viewModel = viewModel<DirectoryViewModel>()
        val navController = NavControllerAmbient.current
        Scaffold(
            topBar = { AppBar() },
            floatingActionButton = { Fab() }
        ) { padding ->
            val directoryList = viewModel.directoryListFlow.collectAsState(emptyList())
            DirectoryList(directoryList.value, Modifier.padding(padding)) {
                navController.navigate(DirectoryFragmentDirections.actionIndividualFragment(it.id))
            }
        }
    }
}

@Composable
private fun AppBar() {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.directory))
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(vectorResource(R.drawable.ic_search_24dp))
            }
            CommonMenu()
        },
    )
}

@Composable
private fun Fab() {
    val navController = NavControllerAmbient.current
    FloatingActionButton({
        navController.navigate(DirectoryFragmentDirections.actionIndividualEditFragment())
    }) {
        Icon(Icons.Filled.Add)
    }
}

@Composable
private fun DirectoryList(directoryList: List<DirectoryItem>, modifier: Modifier = Modifier, onClick: (DirectoryItem) -> Unit = {}) {
    LazyColumnFor(directoryList, modifier = modifier) { item ->
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
