package org.jdc.template.model.webservice

import android.os.Build
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jdc.template.BuildConfig
import org.jdc.template.auth.MyAccountInterceptor
//import org.jdc.template.json.asConverterFactory
import org.jdc.template.model.webservice.colors.ColorService
import retrofit2.Retrofit
import retrofit2.create
import java.io.UnsupportedEncodingException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class ServiceModule {

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
    fun getColorService(@Named(STANDARD_CLIENT) client: OkHttpClient/*, json: Json*/): ColorService {
        val retrofit = Retrofit.Builder()
            .baseUrl(ColorService.BASE_URL)
            .client(client)
//            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create()
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
