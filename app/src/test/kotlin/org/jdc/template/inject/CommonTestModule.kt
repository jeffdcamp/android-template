package org.jdc.template.inject

//import kotlinx.serialization.json.Json
//import org.jdc.template.json.asConverterFactory
import android.app.Application
import com.nhaarman.mockitokotlin2.whenever
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
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
@DisableInstallInCheck // prevent Hilt from checking for @InstallIn (https://issuetracker.google.com/issues/158758786)
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

//    @Provides
//    @Singleton
//    fun provideJson(): Json {
//        return Json {
//            ignoreUnknownKeys = true
//            allowSpecialFloatingPointValues = true
//            useArrayPolymorphism = true
//        }
//    }

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
    fun provideColorService(mockWebServer: MockWebServer/*, json: Json*/): ColorService {
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .client(provideOkHttp())
//            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create(ColorService::class.java)
    }

    @Provides
    @Singleton
    fun provideMockWebServer() = MockWebServer()
}
