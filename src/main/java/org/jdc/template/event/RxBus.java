package org.jdc.template.event;

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

    @Inject
    public RxBus() {
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return bus;
    }

    public final Subscription subscribeMainThread(@Nonnull Action1<? super Object> onNext) {
        return subscribe(AndroidSchedulers.mainThread(), onNext);
    }

    public final Subscription subscribeBackgroundThread(@Nonnull Action1<? super Object> onNext) {
        return subscribe(Schedulers.newThread(), onNext);
    }

    public Subscription subscribe(@Nonnull Scheduler scheduler, @Nonnull Action1<? super Object> onNext) {
        return bus.observeOn(scheduler).subscribe(onNext);
    }
}
