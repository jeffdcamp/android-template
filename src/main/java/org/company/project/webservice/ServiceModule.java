package org.company.project.webservice;

import android.app.Application;
import android.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.company.project.BuildConfig;
import org.company.project.R;
import org.company.project.webservice.websearch.WebSearchService;

import java.io.UnsupportedEncodingException;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.JacksonConverter;

@Module
public class ServiceModule {
    private static final String USER_AGENT_FORMAT = "%s %s / Android %s / %s";

    // Log level
    private RestAdapter.LogLevel serviceLogLevel = RestAdapter.LogLevel.BASIC;

    private Converter defaultConverter = new JacksonConverter(new ObjectMapper());
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

    private void setupStandardHeader(@Nonnull Application application, @Nonnull RequestInterceptor.RequestFacade requestFacade) {
        requestFacade.addHeader("http.useragent", getUserAgent(application));
        requestFacade.addHeader("Accept", "application/json");
    }

    @Nonnull
    private String getBasicAuthHeaderValue(@Nonnull String username, @Nonnull String password) {
        try {
            String basicAuthCredentials = username + ":" + password;
            return "Basic " + Base64.encodeToString(basicAuthCredentials.getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Error encoding auth", e);
        }
    }



    @Provides
    @Singleton
    public WebSearchService getSearchService(final Application application) {
        // Setup RestAdapter
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(WebSearchService.BASE_URL);
        builder.setConverter(defaultConverter);
        builder.setLogLevel(serviceLogLevel);

        // for AUTH
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                setupStandardHeader(application, requestFacade);

                // Setup Basic Auth (if needed)
//                requestFacade.addHeader("Authorization", getBasicAuthHeaderValue(username, password));
            }
        });

        RestAdapter restAdapter = builder.build();
        return restAdapter.create(WebSearchService.class);
    }
}
