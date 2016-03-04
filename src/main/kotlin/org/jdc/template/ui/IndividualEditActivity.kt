package org.jdc.template.ui

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.jdc.template.R
import org.jdc.template.dagger.Injector
import org.jdc.template.event.IndividualSavedEvent
import pocketbus.Bus
import pocketbus.Subscribe
import pocketbus.ThreadMode
import pocketknife.BindExtra
import pocketknife.PocketKnife
import javax.inject.Inject

class IndividualEditActivity : BaseActivity() {


    @BindExtra(EXTRA_ID)
    var individualId: Long = 0

    @Inject
    lateinit var bus: Bus

    private val registrar = IndividualEditActivityRegistrar(this)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_single)
        Injector.get().inject(this)
        PocketKnife.bindExtras(this)

        setSupportActionBar(ab_toolbar)
        enableActionBarBackArrow(true)

        setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL)

        setupActionBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_pos1, IndividualEditFragment.newInstance(individualId)).commit()
        }
    }

    override fun onStart() {
        super.onStart()
        bus.register(registrar)
    }

    override fun onStop() {
        bus.unregister(registrar)
        super.onStop()
    }

    private fun setupActionBar() {
        setSupportActionBar(ab_toolbar)
        val actionBar = supportActionBar

        actionBar?.setTitle(R.string.individual)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item == null) {
            return false
        }

        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    @Subscribe(ThreadMode.MAIN)
    fun handleIndividualSavedEvent1(event: IndividualSavedEvent) {
        finish()
    }

    companion object {

        const val EXTRA_ID = "INDIVIDUAL_ID"
    }
}