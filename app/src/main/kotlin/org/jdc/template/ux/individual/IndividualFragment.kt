package org.jdc.template.ux.individual

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
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.vikingsen.inject.viewmodel.savedstate.SavedStateViewModelFactory
import kotlinx.coroutines.launch
import org.jdc.template.R
import org.jdc.template.databinding.IndividualBinding
import org.jdc.template.inject.Injector
import org.jdc.template.ui.fragment.BaseFragment
import javax.inject.Inject

class IndividualFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactoryFactory: SavedStateViewModelFactory.Factory

    private val viewModel by viewModels<IndividualViewModel> { viewModelFactoryFactory.create(this, requireArguments()) }
    private lateinit var binding: IndividualBinding

    init {
        Injector.get().inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.individual, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this@IndividualFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.setTitle(R.string.individual)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.individual.observeKt {
            binding.individual = it
        }

        // Events
        lifecycleScope.launch {
            for (event in viewModel.eventChannel) {
                when (event) {
                    is IndividualViewModel.Event.EditIndividualEvent -> {
                        val directions = IndividualFragmentDirections.editIndividual(event.individualId)
                        findNavController().navigate(directions)
                    }
                    is IndividualViewModel.Event.IndividualDeletedEvent -> findNavController().popBackStack()
                }
            }
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
        MaterialDialog(requireActivity()).show {
            lifecycleOwner(this@IndividualFragment)
            message(R.string.delete_individual_confirm)
            positiveButton(R.string.delete) {
                viewModel.deleteIndividual()
            }
            negativeButton(R.string.cancel)
        }
    }
}