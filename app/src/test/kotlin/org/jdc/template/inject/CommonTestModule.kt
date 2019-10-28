package org.jdc.template.inject

import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nhaarman.mockitokotlin2.whenever
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.jdc.template.TestFilesystem
import org.jdc.template.model.webservice.colors.ColorService
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class CommonTestModule {
    // ========== ANDROID ==========
    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        val application = mock(Application::class.java)

        whenever(application.filesDir).thenReturn(TestFilesystem.INTERNAL_FILES_DIR)

        doAnswer { invocation ->
            val type = invocation.getArgument<String>(0)
            if (type != null) {
                return@doAnswer File(TestFilesystem.EXTERNAL_FILES_DIR, type)
            } else {
                return@doAnswer TestFilesystem.EXTERNAL_FILES_DIR
            }
        }.whenever(application).getExternalFilesDir(anyString())

        return application
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
    fun provideColorService(mockWebServer: MockWebServer): ColorService {
        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .client(provideOkHttp())
            .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
            .build()

        return retrofit.create(ColorService::class.java)
    }

    @Provides
    @Singleton
    fun provideMockWebServer() = MockWebServer()
}
