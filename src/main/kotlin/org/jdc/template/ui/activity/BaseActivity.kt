package org.jdc.template.ui.activity

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import rx.Subscription
import rx.subscriptions.CompositeSubscription

open class BaseActivity : AppCompatActivity() {

    private var compositeSubscription = CompositeSubscription()

    override fun onStop() {
        compositeSubscription.unsubscribe()
        super.onStop()
    }

    fun addSubscription(subscription: Subscription) {
        if (compositeSubscription.isUnsubscribed) {
            compositeSubscription = CompositeSubscription()
        }
        compositeSubscription.add(subscription)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (allowFinishOnHome() && item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun allowFinishOnHome(): Boolean {
        return true
    }

    fun enableActionBarBackArrow(enable: Boolean) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(enable)
            actionBar.setDisplayHomeAsUpEnabled(enable)
        }
    }
}
