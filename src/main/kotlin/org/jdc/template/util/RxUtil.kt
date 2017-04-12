package org.jdc.template.util


import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

object RxUtil {

    // ***** Retrofit *****

    fun <T> toRetrofitObservable(call: Call<T>): Single<Response<T>> {
        return Single.defer { Single.just<Response<T>>(callRetrofit(call)) }
    }

    fun <T> callRetrofit(call: Call<T>): Response<T>? {
        try {
            return call.execute()
        } catch (e: IOException) {
            Timber.e(e, "Failed to call retrofit execute()")
        }

        return null
    }

    fun <T> verifyRetrofitResponse(response: Response<T>?): T? {
        Timber.i("Retrofit thread: ${Thread.currentThread().name}")

        if (response == null) {
            return null
        }

        if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }
}
