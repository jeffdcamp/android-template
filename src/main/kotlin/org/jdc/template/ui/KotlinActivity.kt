package org.jdc.template.ui

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import de.greenrobot.event.EventBus
import de.greenrobot.event.Subscribe
import org.jdc.template.App
import org.jdc.template.R
import org.jdc.template.domain.individual.KotlinIndividualManager
import org.jdc.template.domain.main.individual.IndividualManager
import pocketknife.PocketKnife
import javax.inject.Inject

open class KotlinActivity : Activity() {
    companion object {
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
    }

    @Inject
    lateinit var individualManager: IndividualManager
    @Inject
    lateinit var kIndividualManager: KotlinIndividualManager
    @Inject
    lateinit var bus: EventBus

//    @BindExtra(KotlinActivity.EXTRA_MESSAGE)
    var message = "Default Text"

//    @SaveState
    var clickCount = 0

//    @Bind(R.id.kotlin_hello_button)
//    lateinit var kotlinButton : Button

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        App.getInjectComponent(this).inject(this)
        ButterKnife.bind(this)
        PocketKnife.bindExtras(this)
        PocketKnife.restoreInstanceState(this, savedInstanceState)
        bus.register(this)

//        var kotlinButton = findViewById(R.id.kotlin_hello_button)
//        kotlinButton.setOnClickListener {
//            bus.post(KotlinButtonClickEvent())
//        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        PocketKnife.saveInstanceState(this, outState)
        super.onSaveInstanceState(outState)
    }

    @OnClick(R.id.kotlin_hello_button)
    fun onButtonClick() {
        bus.post(KotlinButtonClickEvent())
    }

    @Subscribe
    fun handle(event: KotlinButtonClickEvent) {
        clickCount++;
        Toast.makeText(this, "Hello World Kotlin (message: ${message}) (click count: ${clickCount}) (Individual count: ${kIndividualManager.findCount()})", Toast.LENGTH_LONG).show();
    }
}

