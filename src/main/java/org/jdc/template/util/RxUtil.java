package org.jdc.template.util;


import android.support.annotation.Nullable;

import java.io.IOException;

import javax.annotation.Nonnull;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Func0;
import timber.log.Timber;

public final class RxUtil {
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
            Timber.e(e, "Failed to call retrofit execute()");
        }

        return null;
    }

    @Nullable
    public static <T> T verifyRetrofitResponse(@Nullable Response<T> response) {
        if (response == null) {
            return null;
        }

        if (response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }
}
