package org.jdc.template.inject

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.jdc.template.shared.model.webservice.KtorClientDefaults.defaultSetup
import org.jdc.template.shared.model.webservice.ResponseTimePlugin
import org.jdc.template.shared.model.webservice.colors.ColorService
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val commonTestModule = module {
    // ========== ANDROID ==========
//    single<Application> {
//        val application = mockk<Application>(relaxed = true)
//
//        every { application.filesDir } returns TestFilesystem.INTERNAL_FILES_DIR
//
//        every { application.getExternalFilesDir(any()) } answers {
//            val type = firstArg<String?>()
//
//            if (type != null) {
//                File(TestFilesystem.EXTERNAL_FILES_DIR, type)
//            } else {
//                TestFilesystem.EXTERNAL_FILES_DIR
//            }
//        }
//
//        application
//    }

    single<Json> {
        Json {
            ignoreUnknownKeys = true
            allowSpecialFloatingPointValues = true
            useArrayPolymorphism = true
        }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
    }

    single<HttpClient> {
        HttpClient(OkHttp.create()) {
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
        }
    }

    single<ColorService> {
        ColorService(get())
    }
}
