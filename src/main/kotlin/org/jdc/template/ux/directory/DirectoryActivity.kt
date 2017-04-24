package org.jdc.template.ux.directory

import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.directory.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.jdc.template.InternalIntents
import org.jdc.template.R
import org.jdc.template.R.layout.activity_directory
import org.jdc.template.inject.Injector
import org.jdc.template.model.database.main.individual.Individual
import org.jdc.template.ui.activity.DrawerActivity
import org.jdc.template.ui.menu.CommonMenu
import pocketknife.PocketKnife
import javax.inject.Inject

class DirectoryActivity : DrawerActivity(), SearchView.OnQueryTextListener, DirectoryContract.View {
    @Inject
    lateinit var commonMenu: CommonMenu
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var presenter: DirectoryPresenter;

    val adapter = DirectoryAdapter().apply {
        itemClickListener = {
            presenter.individualClicked(it.id)
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
            presenter.newItemClicked()
        }

        setupRecyclerView()

        presenter.init(this)
        presenter.load()
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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        PocketKnife.saveInstanceState(this, outState)
    }

    override fun onResume() {
        super.onResume()
        presenter.reload()
    }

    override fun onStop() {
        presenter.unregister()
        super.onStop()
    }

    override fun showIndividualList(list: List<Individual>) {
        adapter.list = list
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        throw UnsupportedOperationException()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        throw UnsupportedOperationException()
    }

    override fun showNewIndividual() {
        internalIntents.newIndividual(this)
    }

    override fun showIndividual(individualId: Long) {
        internalIntents.showIndividual(this, individualId)
    }
}
