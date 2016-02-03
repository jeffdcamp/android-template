package org.jdc.template.util;


import android.support.annotation.Nullable;
import android.util.Log;

import org.jdc.template.App;

import java.io.IOException;

import javax.annotation.Nonnull;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Func0;

public final class RxUtil {
    public static final String TAG = App.createTag(RxUtil.class);

    private RxUtil() {
    }

    /**
     * Simple call to make any method a deferred Observable call
     */
    public static <T> Observable<T> just(Func0<T> func) {
        return Observable.defer(() -> Observable.just(func.call()));
    }

    /**
     * Simple call to make any method a deferred Observable that emitts each item from an Iterable
     */
    public static <T> Observable<T> from(Func0<Iterable<T>> func) {
        return Observable.defer(() -> Observable.from(func.call()));
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
