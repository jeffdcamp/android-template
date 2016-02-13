package org.jdc.template.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

@Singleton
public class RxBus {
    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    private Map<Class, Object> stickyEventList = new ConcurrentHashMap<>();

    @Inject
    public RxBus() {
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public void postSticky(Object o) {
        stickyEventList.put(o.getClass(), o);
        post(o);
    }

    public void removeSticky(Class eventClass) {
        stickyEventList.remove(eventClass);
    }

    public Observable<Object> toObserverable() {
        return bus;
    }

    public final Subscription subscribeMainThread(@Nonnull Action1<? super Object> onNext) {
        Subscription subscription = subscribe(AndroidSchedulers.mainThread(), onNext);
        postStickyEvents();
        return subscription;
    }

    public final Subscription subscribeBackgroundThread(@Nonnull Action1<? super Object> onNext) {
        Subscription subscription = subscribe(Schedulers.newThread(), onNext);
        postStickyEvents();
        return subscription;
    }

    public Subscription subscribe(@Nonnull Scheduler scheduler, @Nonnull Action1<? super Object> onNext) {
        return bus.observeOn(scheduler).subscribe(onNext);
    }

    private void postStickyEvents() {
        for (Object event : stickyEventList.values()) {
            post(event);
        }
    }
}
