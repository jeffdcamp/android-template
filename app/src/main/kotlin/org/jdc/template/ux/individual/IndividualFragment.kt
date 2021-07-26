package org.jdc.template.ux.individual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.ui.compose.LocalNavController
import org.jdc.template.util.ext.withLifecycleOwner

@AndroidEntryPoint
class IndividualFragment : Fragment() {
    private val viewModel: IndividualViewModel by viewModels()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CompositionLocalProvider(LocalNavController provides findNavController()) {
                    MdcTheme {
                        Surface {
                            IndividualScreen()
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        withLifecycleOwner(this) {
            // Events
            viewModel.eventChannel.receiveWhenStarted { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: IndividualViewModel.Event) {
        when (event) {
            is IndividualViewModel.Event.Navigate -> findNavController().navigate(event.direction)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.individual_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_edit -> {
                viewModel.editIndividual()
                true
            }
            R.id.menu_item_delete -> {
                viewModel.onDeleteClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}