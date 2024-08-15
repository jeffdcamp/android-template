package org.jdc.template.model.webservice

import android.app.Application
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
import org.jdc.template.model.webservice.KtorClientDefaults.defaultSetup
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WebServiceModule {
    @Provides
    @Singleton
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
        }
    }
}
