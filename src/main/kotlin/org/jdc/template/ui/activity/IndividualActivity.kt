package org.jdc.template.ui.activity

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.jdc.template.InternalIntents
import org.jdc.template.R
import org.jdc.template.R.layout.fragment_drawer_single
import org.jdc.template.event.EditIndividualEvent
import org.jdc.template.event.IndividualDeletedEvent
import org.jdc.template.inject.Injector
import org.jdc.template.ui.fragment.IndividualFragment
import pocketbus.Bus
import pocketbus.Subscribe
import pocketbus.SubscriptionRegistration
import pocketbus.ThreadMode
import pocketknife.BindExtra
import pocketknife.PocketKnife
import javax.inject.Inject

class IndividualActivity : DrawerActivity() {

    @BindExtra("INDIVIDUAL_ID")
    var individualId = 0L

    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var bus: Bus

    lateinit var subscriptionRegistration: SubscriptionRegistration

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragment_drawer_single)
        PocketKnife.bindExtras(this)

        setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL)

        setupDrawerWithBackButton(ab_toolbar, R.string.individual)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_pos1, IndividualFragment.newInstance(individualId)).commit()
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

    @Subscribe(ThreadMode.MAIN)
    fun handleEditIndividualEvent8(event: EditIndividualEvent) {
        internalIntents.editIndividual(this, event.id)
    }

    @Subscribe(ThreadMode.MAIN)
    fun handleIndividualDeletedEvent9(event: IndividualDeletedEvent) {
        finish()
    }

    companion object {
        const val EXTRA_ID = "INDIVIDUAL_ID"
    }
}