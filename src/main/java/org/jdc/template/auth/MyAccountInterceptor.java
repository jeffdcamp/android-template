package org.jdc.template.auth;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Inject;

public class MyAccountInterceptor implements Interceptor {
    @Inject
    public MyAccountInterceptor() {
    }

    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        // perform authentication

        return chain.proceed(builder.build());
    }
}