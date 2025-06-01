package org.jdc.template.inject

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.mockk.every
import io.mockk.mockk
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.jdc.template.TestFilesystem
import org.jdc.template.shared.model.webservice.KtorClientDefaults.defaultSetup
import org.jdc.template.shared.model.webservice.ResponseTimePlugin
import org.jdc.template.shared.model.webservice.colors.ColorService
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@DisableInstallInCheck // prevent Hilt from checking for @InstallIn (https://issuetracker.google.com/issues/158758786)
class CommonTestModule {
    // ========== ANDROID ==========
    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        val application = mockk<Application>(relaxed = true)

        every { application.filesDir } returns TestFilesystem.INTERNAL_FILES_DIR

        every { application.getExternalFilesDir(any()) } answers {
            val type = firstArg<String?>()

            if (type != null) {
                File(TestFilesystem.EXTERNAL_FILES_DIR, type)
            } else {
                TestFilesystem.EXTERNAL_FILES_DIR
            }
        }

        return application
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            allowSpecialFloatingPointValues = true
            useArrayPolymorphism = true
        }
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    // TODO: 3/5/24 NAME
    // Per base url
    // Per Auth level
    fun provideKtorHttpClient(): HttpClient {
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
                header(HttpHeaders.AcceptLanguage, "en-US")
            }
//            install(HttpCache) // Talk about cache
        }
        // TODO: 3/5/24 ADD AUTH
    }

    @Provides
    @Singleton
    fun getColorService(httpClient: HttpClient): ColorService {
        return ColorService(httpClient)
    }
}
