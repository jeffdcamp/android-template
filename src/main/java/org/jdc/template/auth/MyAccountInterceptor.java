package org.jdc.template.auth;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

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