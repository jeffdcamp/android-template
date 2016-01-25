package org.jdc.template.webservice;

import android.util.Log;

import org.jdc.template.App;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoggingInterceptor implements Interceptor {

    public enum LogLevel {
        BASIC,
        HEADERS,
        FULL
    }

    private static final String TAG = App.createTag(LoggingInterceptor.class);
    private static final double NANO_IN_MILLIS = 1e6d;

    private final LogLevel logLevel;

    public LoggingInterceptor(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // Log
        long t1 = System.nanoTime();
        Log.i(TAG, String.format("---> %s %s %s", request.url().url().getProtocol(), request.method(), request.url()));
        if (showLog(LogLevel.HEADERS)) {
            Log.i(TAG, String.format("HEADERS%n%s", request.headers()));
        }
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            MediaType mediaType = requestBody.contentType();
            if (mediaType != null && showLog(LogLevel.HEADERS)) {
                Log.i(TAG, String.format("Content-Type: %s", mediaType));
            }
            long contentLength = request.body().contentLength();
            if (contentLength != -1 && showLog(LogLevel.HEADERS)) {
                Log.i(TAG, String.format("Content-Length: %s-byte", contentLength));
            }
        }

        Response response = chain.proceed(request);

        // Log
        long t2 = System.nanoTime();
        Log.i(TAG, String.format("<--- %s %s %s (%.1fms)", response.request().url().url().getProtocol(), response.code(), response.request().url(),
                (t2 - t1) / NANO_IN_MILLIS));
        if (showLog(LogLevel.HEADERS)) {
            Log.i(TAG, String.format("HEADERS%n%s", response.headers()));
        }
        ResponseBody responseBody = response.body();
        if (responseBody != null && showLog(LogLevel.HEADERS)) {
            MediaType mediaType = responseBody.contentType();
            if (mediaType != null) {
                Log.i(TAG, String.format("Content-Type: %s", mediaType));
            }
            long contentLength = responseBody.contentLength();
            if (contentLength != -1 && showLog(LogLevel.HEADERS)) {
                Log.i(TAG, String.format("Content-Length: %s-byte", contentLength));
            }
        }

        return response;
    }

    private boolean showLog(LogLevel minLogLevel) {
        return logLevel.ordinal() >= minLogLevel.ordinal();
    }
}
