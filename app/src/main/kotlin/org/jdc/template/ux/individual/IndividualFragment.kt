package org.jdc.template.ux.individual

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import org.jdc.template.R
import org.jdc.template.databinding.IndividualBinding
import org.jdc.template.inject.Injector
import org.jdc.template.ui.fragment.BaseFragment
import javax.inject.Inject

class IndividualFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(IndividualViewModel::class.java) }
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
        binding.apply {
            viewModel = this@IndividualFragment.viewModel
            setLifecycleOwner(this@IndividualFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.setTitle(R.string.individual)
        enableActionBarBackArrow(true)

        setupViewModelObservers()

        val args = IndividualFragmentArgs.fromBundle(arguments)
        viewModel.setIndividualId(args.individualId)
    }

    private fun setupViewModelObservers() {
        // Events
        viewModel.onEditIndividualEvent.observeNotNull { individualId ->
            val directions = IndividualFragmentDirections.editIndividual()
            directions.setIndividualId(individualId)
            this@IndividualFragment.findNavController().navigate(directions)
        }
        viewModel.onIndividualDeletedEvent.observe {
            this@IndividualFragment.findNavController().popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.individual_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_edit -> {
                viewModel.editTask()
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
            message(R.string.delete_individual_confirm)
            positiveButton(R.string.delete) {
                viewModel.deleteTask()
            }
            negativeButton(R.string.cancel)
        }
    }
}