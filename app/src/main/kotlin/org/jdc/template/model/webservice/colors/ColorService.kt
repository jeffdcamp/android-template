package org.jdc.template.model.webservice.colors

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.plugins.resources.prepareGet
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.resources.Resource
import okio.FileSystem
import okio.Path
import org.jdc.template.model.webservice.colors.dto.ColorsDto
import org.jdc.template.model.webservice.colors.dto.ErrorDto
import org.jdc.template.util.ext.cacheHeaders
import org.jdc.template.util.ext.executeSafely
import org.jdc.template.util.ext.executeSafelyCached
import org.jdc.template.util.ext.saveBodyToFile
import org.jdc.template.util.network.ApiResponse
import org.jdc.template.util.network.CacheApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ColorService
@Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getColorsBySafeArgs(): ApiResponse<out ColorsDto, out Unit> {
        return httpClient.executeSafely({ get(ColorsResource.All()) }) { it.body() }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getColorsToFile(filesystem: FileSystem, file: Path): ApiResponse<out Boolean, out Boolean> {
        return try {
            httpClient.prepareGet(ColorsResource.All()).execute { httpResponse ->
                if (httpResponse.status.isSuccess() && httpResponse.saveBodyToFile(filesystem, file)) {
                    ApiResponse.Success(true)
                } else {
                    Logger.e { "Failed to save colors json (${httpResponse.status}" }
                    ApiResponse.Failure.Error.Unknown(httpResponse.status, "Failed to save colors json (${httpResponse.status}")
                }
            }
        } catch (expected: Throwable) {
            return ApiResponse.Failure.Exception(expected) as ApiResponse<Boolean, Boolean> // This is unnecessary, but the compiler doesn't know that.
        }
    }

    suspend fun getColorsByFullUrl(): ApiResponse<out ColorsDto, out Unit> {
        return httpClient.executeSafely(
            { get("https://raw.githubusercontent.com/jeffdcamp/android-template/33017aa38f59b3ff728a26c1ee350e58c8bb9647/src/test/json/rest-test.json") }
        ) {
            it.body()
        }
    }

    suspend fun postColors(colorsDto: ColorsDto): ApiResponse<out Unit, out ErrorDto> {
        return httpClient.executeSafely(
            {
                post(ColorsResource.All()) {
                    setBody(colorsDto)
                    contentType(ContentType.Application.Json)
                }
            },
            mapClientError = {
                ApiResponse.Failure.Error.Client(ErrorDto(it.status.value, it.status.description))
            },
            mapSuccess = { }
        )
    }

    suspend fun getSearch(query: String): ApiResponse<out Unit, out ErrorDto> {
        return httpClient.executeSafely(
            { get(ColorsResource.Search(query = query)) },
            mapClientError = {
                ApiResponse.Failure.Error.Client(ErrorDto(it.status.value, it.status.description))
            },
            mapSuccess = { it.body() }
        )
    }

    suspend fun getColor(id: Long): ApiResponse<out Unit, out ErrorDto> {
        return httpClient.executeSafely(
            { get(ColorsResource.Color(id = id)) }, // json/{id}
            mapClientError = {
                ApiResponse.Failure.Error.Client(ErrorDto(it.status.value, it.status.description))
            },
            mapSuccess = { it.body() }
        )
    }

    suspend fun getColorHsl(id: Long): ApiResponse<out Unit, out ErrorDto> {
        return httpClient.executeSafely(
            { get(ColorsResource.Color(id = id, format = "hsl")) }, // json/{id}?format=hsl
            mapClientError = {
                ApiResponse.Failure.Error.Client(ErrorDto(it.status.value, it.status.description))
            },
            mapSuccess = { it.body() }
        )
    }

    suspend fun getColorsCached(etag: String?, lastModified: String? = null): CacheApiResponse<out ColorsDto, out Unit> {
        return httpClient.executeSafelyCached(
            {
                get(ColorsResource.All()) {
                    cacheHeaders(etag = etag, lastModified = lastModified)
                }
            },
            mapSuccess = { it.body() }
        )
    }
}

@Resource("json")
private object ColorsResource {
    @Resource("rest-test.json") // json/rest-test.json
    class All(val parent: ColorsResource = ColorsResource)
    @Resource("rest-test.json") // json/rest-test.json?id=<colorId>
    class Search(val parent: ColorsResource = ColorsResource, val query: String)

    @Resource("{id}") // json/{id}?format=<format>
    class Color(val parent: ColorsResource = ColorsResource, val id: Long, val format: String? = null)
}
