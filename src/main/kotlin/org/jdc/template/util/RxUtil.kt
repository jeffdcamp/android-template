package org.jdc.template.util


import retrofit2.Call
import retrofit2.Response
import rx.Observable
import timber.log.Timber
import java.io.IOException

object RxUtil {
    /**
     * Simple call to make any method a deferred Observable call
     */
    fun <T> just(func: () -> T): Observable<T> {
        return Observable.defer { Observable.just(func()) }
    }

    /**
     * Simple call to make any method a deferred Observable that emitts each item from an Iterable
     */
    fun <T> from(func: () -> Iterable<T>): Observable<T> {
        return Observable.defer { Observable.from(func()) }
    }

    // ***** Retrofit *****

    fun <T> toRetrofitObservable(call: Call<T>): Observable<Response<T>> {
        return Observable.defer { Observable.just<Response<T>>(callRetrofit(call)) }
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
