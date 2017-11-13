package org.jdc.template.ux.directory

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.directory.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Int
import org.jdc.template.InternalIntents
import org.jdc.template.R
import org.jdc.template.R.layout.activity_directory
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

    private val adapter = DirectoryAdapter().apply {
        itemClickListener = {
            showIndividual(it.id)
        }
    }

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_directory)

        super.setupDrawerWithDrawerButton(mainToolbar, R.string.drawer_main)

        newFloatingActionButton.setOnClickListener {
            viewModel.addIndividual()
        }

        setupRecyclerView()

        savedInstanceState?.let { restoreState(it) }

        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewModel.directoryList.observeNotNull { list ->
            adapter.list = list
        }

        // Events
        viewModel.onNewIndividualEvent.observe {
            showNewIndividual()
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
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
        recyclerView.scrollToPosition(scrollPosition)
    }

    private fun getListScrollPosition(): Int {
        return recyclerView.getScrollPosition()
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
