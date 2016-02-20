package org.jdc.template.ui

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.fragment_dual.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.jdc.template.InternalIntents
import org.jdc.template.R
import org.jdc.template.R.layout.directory_list
import org.jdc.template.dagger.Injector
import org.jdc.template.event.DirectoryItemSelectedEvent
import org.jdc.template.event.EditIndividualEvent
import org.jdc.template.event.RxBus
import org.jdc.template.ui.menu.CommonMenu
import javax.inject.Inject

class DirectoryActivity : DrawerActivity() {

    @Inject
    lateinit var commonMenu: CommonMenu
    @Inject
    lateinit var internalIntents: InternalIntents

    @Inject
    lateinit var bus: RxBus

    private var dualPane = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(directory_list)
        Injector.get().inject(this)

        setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL)

        super.setupDrawerWithDrawerButton(ab_toolbar, R.string.drawer_main)

        dualPane = fragment_pos2 != null

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_pos1, DirectoryFragment.newInstance(dualPane)).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.common_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onStart() {
        super.onStart()

        addSubscription(bus.subscribeMainThread{event -> handleSubscribeMainThread(event)})
        addSubscription(bus.subscribeBackgroundThread{ event ->  handleSubscribeIoThread(event)})
    }

    private fun handleSubscribeIoThread(event: Any) {
        // ThreadMode.Async because this cannot be called from a onLoadFinished call
        if (event is DirectoryItemSelectedEvent) {
            val id = event.id
            if (dualPane) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_pos2, IndividualFragment.newInstance(id)).commit()
            } else {
                internalIntents.showIndividual(this, id)
            }
        }
    }

    private fun handleSubscribeMainThread(event: Any) {
        if (event is EditIndividualEvent) {
            val id = event.id

            if (dualPane) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_pos2, IndividualEditFragment.newInstance(id)).commit()
            } else {
                internalIntents.editIndividual(this, id)
            }
        }
    }

    override fun allowFinishOnHome(): Boolean {
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return commonMenu.onOptionsItemSelected(this, item) || super.onOptionsItemSelected(item)
    }
}
