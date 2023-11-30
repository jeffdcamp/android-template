package org.jdc.template.model.webservice.colors

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiationConfig
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LoggingConfig
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.resources.prepareGet
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import io.ktor.resources.Resource
import okio.FileSystem
import okio.Path
import org.jdc.template.model.webservice.KtorClientDefaults.defaultSetup
import org.jdc.template.model.webservice.colors.dto.ColorsDto
import org.jdc.template.util.ext.ApiResponse
import org.jdc.template.util.ext.executeSafely
import org.jdc.template.util.ext.saveBodyToFile
import io.ktor.client.plugins.resources.get as getResource

class ColorService(
    engine: HttpClientEngine = OkHttp.create(),
    loggingSetup: LoggingConfig.() -> Unit = { defaultSetup() },
    contentNegotiationSetup: ContentNegotiationConfig.() -> Unit = { defaultSetup(allowAnyContentType = true) },
) {
    private val httpClient: HttpClient = HttpClient(engine) {
        install(Logging) {
            loggingSetup()
        }
        install(Resources)
        install(ContentNegotiation) {
            contentNegotiationSetup()
        }

        defaultRequest {
            url("https://raw.githubusercontent.com/jeffdcamp/android-template/33017aa38f59b3ff728a26c1ee350e58c8bb9647/src/test/")
        }
    }

    suspend fun fetchColorsBySafeArgs(): ApiResponse<ColorsDto> {
        return httpClient.executeSafely({ getResource(ColorsResource.All()) }) { it.body() }
    }

    suspend fun fetchColorsToFile(filesystem: FileSystem, file: Path): Boolean {
        return httpClient.prepareGet(ColorsResource.All()).execute { httpResponse ->
            if (httpResponse.status.isSuccess() && httpResponse.saveBodyToFile(filesystem, file)) {
                true
            } else {
                Logger.e { "Failed to save colors json (${httpResponse.status}" }
                false
            }
        }
    }

    suspend fun fetchColorsByFullUrl(): ApiResponse<ColorsDto> {
        return httpClient.executeSafely(
            { get("https://raw.githubusercontent.com/jeffdcamp/android-template/33017aa38f59b3ff728a26c1ee350e58c8bb9647/src/test/json/rest-test.json") }
        ) {
            it.body()
        }
    }
}

@Resource("json")
private object ColorsResource {
    @Resource("rest-test.json")
    class All(val parent: ColorsResource = ColorsResource)
}
