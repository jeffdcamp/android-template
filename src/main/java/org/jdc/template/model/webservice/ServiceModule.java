package org.jdc.template.model.webservice;

import android.os.Build;
import android.util.Base64;

import org.jdc.template.BuildConfig;
import org.jdc.template.auth.MyAccountInterceptor;
import org.jdc.template.model.webservice.colors.ColorService;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class ServiceModule {
    private static final String STANDARD_CLIENT = "STANDARD_CLIENT"; // client without auth
    private static final String AUTHENTICATED_CLIENT = "AUTHENTICATED_CLIENT";
    private static final int DEFAULT_TIMEOUT_MINUTES = 3;
    private static final String USER_AGENT;

    // Log level
    private final HttpLoggingInterceptor.Level serviceLogLevel = HttpLoggingInterceptor.Level.BASIC;

    static {
        USER_AGENT = BuildConfig.USER_AGENT_APP_NAME + " " + BuildConfig.VERSION_NAME + " / " + "Android " + Build.VERSION.RELEASE + " " +
                Build.VERSION.INCREMENTAL + " / " +
                Build.MANUFACTURER +
                " " + Build.MODEL;
    }

    @Provides
    @Named(AUTHENTICATED_CLIENT)
    public OkHttpClient getAuthenticatedClient(@Nonnull MyAccountInterceptor accountInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        setupClient(builder);

        // make sure authenticated connection is done
        builder.addInterceptor(accountInterceptor);

        return builder.build();
    }

    @Provides
    @Named(STANDARD_CLIENT)
    public OkHttpClient getStandardClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        setupClient(builder);

        return builder.build();
    }

    private void setupClient(@Nonnull OkHttpClient.Builder clientBuilder) {
        clientBuilder.connectTimeout(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);
        clientBuilder.readTimeout(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);

        clientBuilder.addInterceptor(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder.addHeader("http.useragent", USER_AGENT);
            requestBuilder.addHeader("Accept", "application/json");
            return chain.proceed(requestBuilder.build());
        });

        clientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(serviceLogLevel));
    }

    private void setupBasicAuth(@Nonnull OkHttpClient.Builder clientBuilder, @Nonnull String username, @Nonnull String password) {
        try {
            String basicAuthCredentials = username + ":" + password;
            final String auth = "Basic " + Base64.encodeToString(basicAuthCredentials.getBytes("UTF-8"), Base64.NO_WRAP);


            clientBuilder.addInterceptor(chain -> {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Authorization", auth);
                return chain.proceed(builder.build());
            });
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Error encoding auth", e);
        }
    }

    @Provides
    @Singleton
    public ColorService getColorService(@Nonnull @Named(STANDARD_CLIENT) OkHttpClient client, JacksonConverterFactory jacksonConverterFactory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ColorService.BASE_URL)
                .client(client)
                .addConverterFactory(jacksonConverterFactory)
                .build();

        return retrofit.create(ColorService.class);
    }
}
