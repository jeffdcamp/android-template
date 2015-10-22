package org.jdc.template.webservice;

import android.app.Application;
import android.util.Base64;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jdc.template.BuildConfig;
import org.jdc.template.R;
import org.jdc.template.auth.MyAccountInterceptor;
import org.jdc.template.webservice.websearch.WebSearchService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module
public class ServiceModule {
    private static final String USER_AGENT_FORMAT = "%s %s / Android %s / %s";
    private static final String STANDARD_CLIENT = "STANDARD_CLIENT"; // client without auth
    private static final String AUTHENTICATED_CLIENT = "AUTHENTICATED_CLIENT";
    private static final int DEFAULT_TIMEOUT_MINUTES = 3;

    // Log level
    private LoggingInterceptor.LogLevel serviceLogLevel = LoggingInterceptor.LogLevel.BASIC;
    private String userAgent;

    public String getUserAgent(@Nonnull Application application) {
        if (userAgent == null || userAgent.isEmpty()) {
            userAgent = String.format(USER_AGENT_FORMAT,
                    application.getString(R.string.app_name),
                    BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")",
                    android.os.Build.VERSION.RELEASE,
                    android.os.Build.MODEL);
        }

        return userAgent;
    }

    @Provides
    @Named(AUTHENTICATED_CLIENT)
    public OkHttpClient getAuthenticatedClient(@Nonnull MyAccountInterceptor accountInterceptor) {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        client.setConnectTimeout(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);

        client.interceptors().add(accountInterceptor);

        return client;
    }

    @Provides
    @Named(STANDARD_CLIENT)
    public OkHttpClient getStandardClient() {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        client.setConnectTimeout(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        return client;
    }

    private void setupStandardHeader(@Nonnull final Application application, @Nonnull OkHttpClient client) {
        List<Interceptor> interceptors = client.interceptors();
        interceptors.add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("http.useragent", ServiceModule.this.getUserAgent(application))
                        .addHeader("Accept", "application/json");
                return chain.proceed(builder.build());
            }
        });

        interceptors.add(new LoggingInterceptor(serviceLogLevel));
    }

    private void setupBasicAuth(@Nonnull final Application application, @Nonnull OkHttpClient client, @Nonnull String username, @Nonnull String password) {
        try {
            String basicAuthCredentials = username + ":" + password;
            final String auth = "Basic " + Base64.encodeToString(basicAuthCredentials.getBytes("UTF-8"), Base64.NO_WRAP);

            List<Interceptor> interceptors = client.interceptors();

            interceptors.add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
                    builder.addHeader("Authorization", auth);
                    return chain.proceed(builder.build());
                }
            });
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Error encoding auth", e);
        }
    }

    @Provides
    @Singleton
    public WebSearchService getSearchService(@Nonnull final Application application,
                                             @Nonnull @Named(STANDARD_CLIENT) OkHttpClient client,
                                             @Nonnull GsonConverterFactory converterFactory) {
        setupStandardHeader(application, client);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebSearchService.BASE_URL)
                .client(client)
                .addConverterFactory(converterFactory)
                .build();

        return retrofit.create(WebSearchService.class);
    }
}
