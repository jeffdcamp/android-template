package org.jdc.template.ux.directory

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Int
import org.jdc.template.InternalIntents
import org.jdc.template.R
import org.jdc.template.databinding.DirectoryActivityBinding
import org.jdc.template.inject.Injector
import org.jdc.template.ui.activity.DrawerActivity
import org.jdc.template.ui.menu.CommonMenu
import org.jdc.template.util.getScrollPosition
import javax.inject.Inject

class DirectoryActivity : DrawerActivity(), SearchView.OnQueryTextListener {
    @Inject
    lateinit var commonMenu: CommonMenu
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DirectoryViewModel::class.java) }
    private lateinit var binding: DirectoryActivityBinding

    private val adapter by lazy {
        DirectoryAdapter(viewModel).apply {
            itemClickListener = {
                showIndividual(it.id)
            }
        }
    }

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
        super.setupDrawerWithDrawerButton(findViewById(R.id.mainToolbar), R.string.drawer_main)

        binding.newFloatingActionButton.setOnClickListener {
            viewModel.addIndividual()
        }

        setupRecyclerView()

        savedInstanceState?.let { restoreState(it) }

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
//        viewModel.directoryList.observeNotNull { list ->
//            adapter.items = list
//        }

        // Events
        viewModel.onNewIndividualEvent.observe {
            showNewIndividual()
        }
        viewModel.showIndividualEvent.observeNotNull {
            showIndividual(it)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.directory_menu, menu)
        menuInflater.inflate(R.menu.common_menu, menu)

        val searchMenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = MenuItemCompat.getActionView(searchMenuItem) as SearchView
        searchView.queryHint = getString(R.string.menu_search_hint)
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return commonMenu.onOptionsItemSelected(this, item) || super.onOptionsItemSelected(item)
    }

    override fun allowFinishOnHome(): Boolean {
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveState(outState)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        throw UnsupportedOperationException()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        throw UnsupportedOperationException()
    }

    private fun showNewIndividual() {
        internalIntents.newIndividual(this)
    }

    private fun showIndividual(individualId: Long) {
        internalIntents.showIndividual(this, individualId)
    }

    private fun scrollToPosition(scrollPosition: Int) {
        binding.recyclerView.scrollToPosition(scrollPosition)
    }

    private fun getListScrollPosition(): Int {
        return binding.recyclerView.getScrollPosition()
    }

    private fun restoreState(bundle: Bundle) {
        with(SaveStateOptions) {
            scrollToPosition(bundle.scrollPosition!!)
        }
    }

    private fun saveState(bundle: Bundle) {
        with(SaveStateOptions) {
            bundle.scrollPosition = getListScrollPosition()
        }
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, DirectoryActivity::class)

    object IntentOptions

    object SaveStateOptions {
        var Bundle.scrollPosition by BundleExtra.Int()
    }
}
