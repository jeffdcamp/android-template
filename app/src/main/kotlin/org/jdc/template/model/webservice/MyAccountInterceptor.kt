package org.jdc.template.model.webservice

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class MyAccountInterceptor @Inject
constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        // perform authentication

        return chain.proceed(builder.build())
    }
}