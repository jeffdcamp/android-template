package org.jdc.template.ui

import android.os.Bundle
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import org.jdc.template.R
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

//    @Bind(R.id.kotlin_hello_button)
//    lateinit var kotlinButton : Button

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        Injector.get().inject(this)
        ButterKnife.bind(this)
        PocketKnife.bindExtras(this)
        PocketKnife.restoreInstanceState(this, savedInstanceState)


//        var kotlinButton = findViewById(R.id.kotlin_hello_button)
//        kotlinButton.setOnClickListener {
//            bus.send(KotlinButtonClickEvent())
//        }
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

    @OnClick(R.id.kotlin_hello_button)
    fun onButtonClick() {
        bus.send(KotlinButtonClickEvent())
    }

    fun handleButtonClick() {
        clickCount++;
        Toast.makeText(this, "Hello World Kotlin (message: ${message}) (click count: ${clickCount}) (Individual count: ${kIndividualManager.findCount()})", Toast.LENGTH_LONG).show();
    }
}

