package org.jdc.template.event

import rx.Observable
import rx.Scheduler
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RxBus
@Inject
constructor() {
    private val bus = SerializedSubject(PublishSubject.create<Any>())

    private val stickyEventList = ConcurrentHashMap<Class<Any>, Any>()

    fun post(o: Any) {
        bus.onNext(o)
    }

    fun postSticky(o: Any) {
        stickyEventList.put(o.javaClass, o)
        post(o)
    }

    fun removeSticky(eventClass: Class<Any>) {
        stickyEventList.remove(eventClass)
    }

    fun toObserverable(): Observable<Any> {
        return bus
    }

    fun subscribeMainThread(onNext: (Any) -> Unit): Subscription {
        val subscription = subscribe(AndroidSchedulers.mainThread(), onNext)
        postStickyEvents()
        return subscription
    }

    fun subscribeBackgroundThread(onNext: (Any) -> Unit): Subscription {
        val subscription = subscribe(Schedulers.newThread(), onNext)
        postStickyEvents()
        return subscription
    }

    fun subscribe(scheduler: Scheduler, onNext: (Any) -> Unit): Subscription {
        return bus.observeOn(scheduler).subscribe(onNext)
    }

    private fun postStickyEvents() {
        for (event in stickyEventList.values) {
            post(event)
        }
    }
}
