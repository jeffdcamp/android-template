package org.jdc.template.inject

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import io.mockk.every
import io.mockk.mockk
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.jdc.template.TestFilesystem
import org.jdc.template.model.webservice.colors.ColorService
import org.jdc.template.util.json.asConverterFactory
import retrofit2.Retrofit
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
        val application = mockk<Application>()

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
    fun provideColorService(mockWebServer: MockWebServer, json: Json): ColorService {
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .client(provideOkHttp())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create(ColorService::class.java)
    }

    @Provides
    @Singleton
    fun provideMockWebServer() = MockWebServer()
}
