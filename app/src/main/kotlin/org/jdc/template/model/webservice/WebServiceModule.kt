package org.jdc.template.model.webservice

import android.app.Application
import android.os.Build
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jdc.template.BuildConfig
import org.jdc.template.model.webservice.KtorClientDefaults.defaultSetup
import java.io.UnsupportedEncodingException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WebServiceModule {

    // Log level
    private val serviceLogLevel = HttpLoggingInterceptor.Level.BASIC

    @Provides
    @Named(AUTHENTICATED_CLIENT)
    fun getAuthenticatedClient(accountInterceptor: MyAccountInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        setupClient(builder)

        // make sure authenticated connection is done
        builder.addInterceptor(accountInterceptor)

        return builder.build()
    }

    @Provides
    @Named(STANDARD_CLIENT)
    fun getStandardClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        setupClient(builder)

        return builder.build()
    }

    private fun setupClient(clientBuilder: OkHttpClient.Builder) {
        clientBuilder.connectTimeout(DEFAULT_TIMEOUT_MINUTES.toLong(), TimeUnit.MINUTES)
        clientBuilder.readTimeout(DEFAULT_TIMEOUT_MINUTES.toLong(), TimeUnit.MINUTES)

        clientBuilder.addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader("User-Agent", USER_AGENT)
            requestBuilder.addHeader("Accept", "application/json")
            chain.proceed(requestBuilder.build())
        }

        clientBuilder.addInterceptor(HttpLoggingInterceptor().apply { level = serviceLogLevel })
    }

    @Suppress("UnusedPrivateMember") // kept here as a reference
    private fun setupBasicAuth(clientBuilder: OkHttpClient.Builder, username: String, password: String) {
        try {
            clientBuilder.addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Authorization", Credentials.basic(username, password))
                chain.proceed(builder.build())
            }
        } catch (e: UnsupportedEncodingException) {
            throw IllegalStateException("Error encoding auth", e)
        }
    }

    @Provides
    @Singleton
    // TODO: 3/5/24 NAME
    // Per base url
    // Per Auth level
    fun provideKtorHttpClient(application: Application): HttpClient {
        return HttpClient(OkHttp.create()) {
            install(Logging) {
                defaultSetup()
            }
            install(ResponseTimePlugin)
            install(Resources)
            install(ContentNegotiation) {
                defaultSetup(allowAnyContentType = true)
            }

            defaultRequest {
                url("https://raw.githubusercontent.com/jeffdcamp/android-template/33017aa38f59b3ff728a26c1ee350e58c8bb9647/src/test/")
                acceptLanguage(application)
            }
//            install(HttpCache) // Talk about cache
        }
        // TODO: 3/5/24 ADD AUTH
    }

    companion object {
        const val STANDARD_CLIENT = "STANDARD_CLIENT" // client without auth
        const val AUTHENTICATED_CLIENT = "AUTHENTICATED_CLIENT"
        const val DEFAULT_TIMEOUT_MINUTES = 3
        private val USER_AGENT: String = BuildConfig.USER_AGENT_APP_NAME + " " + BuildConfig.VERSION_NAME + " / " + "Android " + Build.VERSION.RELEASE + " " +
                Build.VERSION.INCREMENTAL + " / " +
                Build.MANUFACTURER +
                " " + Build.MODEL
    }
}

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ClientAuthenticated

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ClientNotAuthenticated