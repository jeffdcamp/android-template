package org.jdc.template.util;


import android.support.annotation.Nullable;
import android.util.Log;

import org.jdc.template.App;

import java.io.IOException;

import javax.annotation.Nonnull;

import retrofit.Call;
import retrofit.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public final class RxUtil {
    public static final String TAG = App.createTag(RxUtil.class);

    private RxUtil() {
    }

    /**
     * Create a simple observable that runs in the background and subscribes on the foreground
     */
    public static <T> Observable<T> simpleBackgroundRx(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Simple call to make any method a deferred Observable call
     * NOTE: Because Supplier is not available on Android, use Func0 instead
     */
    public static <T> Observable<T> toObservable(Func0<T> func) {
        return Observable.defer(() -> Observable.just(func.call()));
    }

    // ***** Retrofit *****

    public static <T> Observable<Response<T>> toRetrofitObservable(Call<T> call) {
        return Observable.defer(() -> Observable.just(callRetrofit(call)));
    }

    @Nullable
    public static <T> Response<T> callRetrofit(@Nonnull Call<T> call) {
        try {
            return call.execute();
        } catch (IOException e) {
            Log.e(TAG, "Failed to call retrofit execute()", e);
        }

        return null;
    }

    @Nullable
    public static <T> T verifyRetrofitResponse(@Nullable Response<T> response) {
        Log.e(TAG, "Retrofit thread: " + Thread.currentThread().getName());

        if (response == null) {
            return null;
        }

        if (response.isSuccess()) {
            return response.body();
        } else {
            return null;
        }
    }
}
