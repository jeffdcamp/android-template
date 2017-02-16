package org.jdc.template.ui.activity

import android.app.Activity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.directory.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.jdc.template.Analytics
import org.jdc.template.InternalIntents
import org.jdc.template.R
import org.jdc.template.R.layout.activity_directory
import org.jdc.template.inject.Injector
import org.jdc.template.model.database.main.individual.Individual
import org.jdc.template.model.database.main.individual.IndividualConst
import org.jdc.template.model.database.main.individual.IndividualManager
import org.jdc.template.ui.adapter.DirectoryAdapter
import org.jdc.template.ui.menu.CommonMenu
import pocketknife.PocketKnife
import pocketknife.SaveState
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class DirectoryActivity : DrawerActivity(), SearchView.OnQueryTextListener, DirectoryAdapter.OnItemClickListener {
    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var commonMenu: CommonMenu
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var individualManager: IndividualManager

    @SaveState
    var lastSelectedId = 0L

    lateinit var adapter: DirectoryAdapter

    var modelTs = 0L

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_directory)

        setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL)

        super.setupDrawerWithDrawerButton(ab_toolbar, R.string.drawer_main)


        newFloatingActionButton.setOnClickListener {
            onNewItemClick()
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = DirectoryAdapter(this)
        adapter.listener = this
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

        lastSelectedId = adapter.lastSelectedItemId

        PocketKnife.saveInstanceState(this, outState)
    }

    override fun onStart() {
        super.onStart()
        if (modelTs != individualManager.lastTableModifiedTs) {
            modelTs = individualManager.lastTableModifiedTs
            loadList()
        }
    }

    fun loadList() {
        val observable = individualManager.findAllBySelectionRx(orderBy = "${IndividualConst.C_FIRST_NAME}, ${IndividualConst.C_LAST_NAME}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addSubscription(observable.subscribe { data -> dataLoaded(data) })
    }

    fun dataLoaded(data: List<Individual>) {
        adapter.set(data)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        throw UnsupportedOperationException()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        throw UnsupportedOperationException()
    }

    override fun onItemClick(selectedItemId: Long) {
        internalIntents.showIndividual(this, selectedItemId)
    }

    fun onNewItemClick() {
        analytics.send(HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                .setAction(Analytics.ACTION_NEW)
                .build())

        internalIntents.editIndividual(this, -1)
    }
}
