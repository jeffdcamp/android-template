package org.jdc.template.util;


import android.support.annotation.Nullable;

import java.io.IOException;

import javax.annotation.Nonnull;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public final class RxUtil {
    private RxUtil() {
    }

    // ***** Retrofit *****

    public static <T> Single<Response<T>> toRetrofitObservable(Call<T> call) {
        return Single.defer(() -> Single.just(callRetrofit(call)));
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
