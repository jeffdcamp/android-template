package org.jdc.template.auth

import java.io.IOException

import javax.inject.Inject

import okhttp3.Interceptor
import okhttp3.Response

class MyAccountInterceptor @Inject
constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        // perform authentication

        return chain.proceed(builder.build())
    }
}