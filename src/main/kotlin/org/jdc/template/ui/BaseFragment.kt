package org.jdc.template.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rx.Subscription
import rx.subscriptions.CompositeSubscription

abstract class BaseFragment : Fragment() {

    private var compositeSubscription = CompositeSubscription()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(layoutResourceId, container, false)
        onPostCreateView()
        return view
    }

    protected abstract val layoutResourceId: Int

    protected fun onPostCreateView() {
    }

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
}
