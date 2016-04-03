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
import pocketbus.SubscriptionRegistration
import pocketbus.ThreadMode
import pocketknife.BindExtra
import javax.inject.Inject

class IndividualEditActivity : BaseActivity() {

    @BindExtra(EXTRA_ID)
    var individualEditId: Long = 0

    @Inject
    lateinit var bus: Bus

    lateinit var subscriptionRegistration: SubscriptionRegistration

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_single)
        Injector.get().inject(this)

        // temp fix for kapt issue (https://youtrack.jetbrains.com/issue/KT-9183)
        individualEditId = getIntent().getLongExtra(EXTRA_ID, 0);
//        PocketKnife.bindExtras(this)

        setSupportActionBar(ab_toolbar)
        enableActionBarBackArrow(true)

        setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL)

        setupActionBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_pos1, IndividualEditFragment.newInstance(individualEditId)).commit()
        }
    }

    override fun onStart() {
        super.onStart()
        subscriptionRegistration = bus.register(this)
    }

    override fun onStop() {
        bus.unregister(subscriptionRegistration)
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