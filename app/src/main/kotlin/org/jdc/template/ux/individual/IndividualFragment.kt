package org.jdc.template.ux.individual

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.vikingsen.inject.viewmodel.savedstate.SavedStateViewModelFactory
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
        binding.viewModel = this@IndividualFragment.viewModel
        binding.lifecycleOwner = this@IndividualFragment

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.setTitle(R.string.individual)
        enableActionBarBackArrow(true)

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        // Events
        viewModel.onEditIndividualEvent.observeNotNull { individualId ->
            val directions = IndividualFragmentDirections.editIndividual(individualId)
            findNavController().navigate(directions)
        }
        viewModel.onIndividualDeletedEvent.observe {
            findNavController().popBackStack()
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