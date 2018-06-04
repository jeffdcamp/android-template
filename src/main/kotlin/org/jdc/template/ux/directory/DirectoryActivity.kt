package org.jdc.template.ux.directory

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Int
import org.jdc.template.InternalIntents
import org.jdc.template.R
import org.jdc.template.databinding.DirectoryActivityBinding
import org.jdc.template.ext.getScrollPosition
import org.jdc.template.inject.Injector
import org.jdc.template.ui.activity.DrawerActivity
import org.jdc.template.ui.menu.CommonMenu
import javax.inject.Inject

class DirectoryActivity : DrawerActivity() {
    @Inject
    lateinit var commonMenu: CommonMenu
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DirectoryViewModel::class.java) }
    private lateinit var binding: DirectoryActivityBinding
    private val adapter by lazy { DirectoryAdapter(viewModel) }

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.directory_activity)
        binding.apply {
            viewModel = this@DirectoryActivity.viewModel
            setLifecycleOwner(this@DirectoryActivity)
        }

        super.setupDrawerWithDrawerButton(binding.appbar.mainToolbar, R.string.drawer_main)

        setupRecyclerView()

        savedInstanceState?.let { restoreState(it) }

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        // Events
        viewModel.onNewIndividualEvent.observe {
            showNewIndividual()
        }
        viewModel.showIndividualEvent.observeNotNull {
            showIndividual(it)
        }
    }

    private fun setupRecyclerView() {
        if (resources.getBoolean(R.bool.isTablet)) {
            binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        } else {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.directory_menu, menu)
        menuInflater.inflate(R.menu.common_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return commonMenu.onOptionsItemSelected(this, item) || super.onOptionsItemSelected(item)
    }

    // Allow for Nav Drawer
    override fun allowFinishOnHome() = false

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        with(SaveStateOptions) {
            bundle.scrollPosition = binding.recyclerView.getScrollPosition()
        }
    }

    private fun showNewIndividual() {
        internalIntents.newIndividual(this)
    }

    private fun showIndividual(individualId: Long) {
        internalIntents.showIndividual(this, individualId)
    }

    private fun restoreState(bundle: Bundle) {
        with(SaveStateOptions) {
            binding.recyclerView.scrollToPosition(bundle.scrollPosition!!)
        }
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, DirectoryActivity::class)

    object IntentOptions

    object SaveStateOptions {
        var Bundle.scrollPosition by BundleExtra.Int()
    }
}
