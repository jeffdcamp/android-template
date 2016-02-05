package org.jdc.template.ui

import android.os.Bundle
import android.widget.Toast
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_kotlin.*
import org.jdc.template.R.layout.activity_kotlin
import org.jdc.template.dagger.Injector
import org.jdc.template.domain.main.individual.IndividualManager
import org.jdc.template.event.RxBus
import pocketknife.PocketKnife
import javax.inject.Inject

open class KotlinActivity : BaseActivity() {
    companion object {
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
    }

    @Inject
    lateinit var kIndividualManager: IndividualManager
    @Inject
    lateinit var bus: RxBus

//    @BindExtra(KotlinActivity.EXTRA_MESSAGE)
    var message = "Default Text"

//    @SaveState
    var clickCount = 0

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_kotlin)
        Injector.get().inject(this)
        ButterKnife.bind(this)
        PocketKnife.bindExtras(this)
        PocketKnife.restoreInstanceState(this, savedInstanceState)


        helloButton.setOnClickListener() {
            bus.send(KotlinButtonClickEvent())
        }
    }

    override fun onStart() {
        super.onStart()
        addSubscription(bus.subscribeMainThread { event -> handleSubscribeMainThread(event) })
    }

    private fun handleSubscribeMainThread(event: Any?) {
        when (event) {
            is KotlinButtonClickEvent -> handleButtonClick()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        PocketKnife.saveInstanceState(this, outState)
        super.onSaveInstanceState(outState)
    }

    fun handleButtonClick() {
        clickCount++;
        Toast.makeText(this, "Hello World Kotlin (message: ${message}) (click count: ${clickCount}) (Individual count: ${kIndividualManager.findCount()})", Toast.LENGTH_LONG).show();
    }
}

