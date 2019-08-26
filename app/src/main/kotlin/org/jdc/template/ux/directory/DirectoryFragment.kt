package org.jdc.template.ux.directory

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikingsen.inject.viewmodel.ViewModelFactory
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Int
import org.jdc.template.R
import org.jdc.template.databinding.DirectoryBinding
import org.jdc.template.ext.getScrollPosition
import org.jdc.template.inject.Injector
import org.jdc.template.ui.fragment.BaseFragment
import org.jdc.template.ui.menu.CommonMenu
import javax.inject.Inject


class DirectoryFragment : BaseFragment() {
    @Inject
    lateinit var commonMenu: CommonMenu
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy<DirectoryViewModel> { ViewModelProviders.of(this, viewModelFactory).get() }
    private lateinit var binding: DirectoryBinding
    private val adapter by lazy { DirectoryAdapter(viewModel) }

    init {
        Injector.get().inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.directory, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = this@DirectoryFragment.viewModel
            lifecycleOwner = this@DirectoryFragment
        }

        setupRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.setTitle(R.string.app_name)

        enableActionBarBackArrow(false)

        savedInstanceState?.let { restoreState(it) }

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        // Events
        viewModel.onNewIndividualEvent.observeKt {
            showNewIndividual()
        }
        viewModel.showIndividualEvent.observeKt {
            showIndividual(it)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
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
        with(SaveStateOptions) {
            if (::binding.isInitialized) { // onSaveInstanceState may be called before onCreateView
                bundle.scrollPosition = binding.recyclerView.getScrollPosition()
            }
        }
    }

    private fun showNewIndividual() {
        findNavController().navigate(DirectoryFragmentDirections.newIndividual())
    }

    private fun showIndividual(individualId: Long) {
        val directions = DirectoryFragmentDirections.showIndividual(individualId)
        findNavController().navigate(directions)
    }

    private fun restoreState(bundle: Bundle) {
        with(SaveStateOptions) {
            binding.recyclerView.scrollToPosition(bundle.scrollPosition ?: 0)
        }
    }

    object SaveStateOptions {
        var Bundle.scrollPosition by BundleExtra.Int()
    }
}
