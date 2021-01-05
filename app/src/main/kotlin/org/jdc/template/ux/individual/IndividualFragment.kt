package org.jdc.template.ux.individual

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import org.jdc.template.R
import org.jdc.template.databinding.IndividualFragmentBinding
import org.jdc.template.ext.autoCleared
import org.jdc.template.ext.withLifecycleOwner

@AndroidEntryPoint
class IndividualFragment : Fragment() {
    private val viewModel: IndividualViewModel by viewModels()

    private var binding: IndividualFragmentBinding by autoCleared()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = IndividualFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this@IndividualFragment

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        withLifecycleOwner(this) {
            viewModel.individualFlow.collectWhenStarted {
                binding.individual = it
            }

            // Events
            viewModel.eventChannel.receiveWhenStarted { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: IndividualViewModel.Event) {
        when (event) {
            is IndividualViewModel.Event.EditIndividualEvent -> {
                val directions = IndividualFragmentDirections.actionToIndividualEditFragment(event.individualId)
                findNavController().navigate(directions)
            }
            is IndividualViewModel.Event.IndividualDeletedEvent -> findNavController().popBackStack()
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
                promptDeleteIndividual()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun promptDeleteIndividual() {
        MaterialAlertDialogBuilder(requireActivity())
            .setMessage(R.string.delete_individual_confirm)
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteIndividual()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .show()
    }
}