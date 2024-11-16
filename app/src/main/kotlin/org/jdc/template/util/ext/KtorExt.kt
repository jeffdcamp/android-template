@file:Suppress("unused")

package org.jdc.template.util.ext

import android.app.Application
import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.etag
import io.ktor.http.ifNoneMatch
import io.ktor.http.isSuccess
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
import io.ktor.utils.io.readRemaining
import io.ktor.utils.io.writeFully
import kotlinx.io.IOException
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path
import okio.buffer
import org.jdc.template.util.network.ApiResponse
import org.jdc.template.util.network.CacheApiResponse

@Suppress("kotlin:S6312") // Sonar issue with Coroutine scope on function ext
suspend fun <T, E> HttpClient.executeSafely(
    apiCall: suspend HttpClient.() -> HttpResponse,
    mapClientError: suspend (HttpResponse) -> ApiResponse.Failure.Error.Client<E> = {
        ApiResponse.Failure.Error.Client(null, "Error executing service call: ${it.request.method.value} ${it.request.url} (${it.status})")
    },
    mapException: suspend (Throwable) -> ApiResponse.Failure.Exception = { ApiResponse.Failure.Exception(it) },
    mapSuccess: suspend (HttpResponse) -> T,
): ApiResponse<out T, out E> {
    return try {
        val response = apiCall()
        if (response.status.isSuccess()) {
            ApiResponse.Success(mapSuccess(response))
        } else {
            val message = "Error executing service call: ${response.request.method.value} ${response.request.url} (${response.status})"
            when (response.status) {
                HttpStatusCode.Forbidden -> ApiResponse.Failure.Error.Forbidden(message)
                HttpStatusCode.NoToken -> ApiResponse.Failure.Error.NoToken(message)
                in HttpStatusCode(400, "")..HttpStatusCode(499, "") -> mapClientError(response)
                in HttpStatusCode(500, "")..HttpStatusCode(599, "") -> ApiResponse.Failure.Error.Server(message)
                else -> ApiResponse.Failure.Error.Unknown(response.status, message)
            }
        }
    } catch (expected: Throwable) {
        mapException(expected)
    }
}

private val HttpStatusCode.Companion.NoToken: HttpStatusCode
    get() = HttpStatusCode(480, "No Token")

@Suppress("kotlin:S6312") // Sonar issue with Coroutine scope on function ext
suspend fun <T, E> HttpClient.executeSafelyCached(
    apiCall: suspend HttpClient.() -> HttpResponse,
    mapClientError: suspend (HttpResponse) -> CacheApiResponse.Failure.Error.Client<E> = {
        CacheApiResponse.Failure.Error.Client(null, "Error executing service call: ${it.request.method.value} ${it.request.url} (${it.status})")
    },
    mapException: suspend (Throwable) -> CacheApiResponse.Failure.Exception = { CacheApiResponse.Failure.Exception(it) },
    mapSuccess: suspend (HttpResponse) -> T,
): CacheApiResponse<out T, out E> {
    return try {
        val response = apiCall()
        if (response.status == HttpStatusCode.NotModified) {
            CacheApiResponse.Success(null, response.etag(), response.headers[HttpHeaders.LastModified])
        } else if (response.status.isSuccess()) {
            CacheApiResponse.Success(mapSuccess(response), response.etag(), response.headers[HttpHeaders.LastModified])
        } else {
            val message = "Error executing service call: ${response.request.method.value} ${response.request.url} (${response.status})"
            when (response.status) {
                HttpStatusCode.Forbidden -> CacheApiResponse.Failure.Error.Forbidden(message)
                HttpStatusCode.NoToken -> CacheApiResponse.Failure.Error.NoToken(message)
                in HttpStatusCode(400, "")..HttpStatusCode(499, "") -> mapClientError(response)
                in HttpStatusCode(500, "")..HttpStatusCode(599, "") -> CacheApiResponse.Failure.Error.Server(message)
                else -> CacheApiResponse.Failure.Error.Unknown(response.status, message)
            }
        }
    } catch (expected: Throwable) {
        mapException(expected)
    }
}

/**
 * Write Ktor HttpResponse stream to file
 *
 * @param fileSystem Filesystem to save the file outputFile to
 * @param outputFile target file.  If the file already exists, then remove the file
 * @return true if the file streamed successfully to file and the new file exists
 */
@Suppress("kotlin:S6312")
suspend fun HttpResponse.saveBodyToFile(fileSystem: FileSystem, outputFile: Path): Boolean {
    Logger.d { "Saving response [${call.request.url}] to file [$outputFile]..." }
    var success = false
    try {
        // if the target file already exists, remove it.
        fileSystem.delete(outputFile)

        val byteReadChannel: ByteReadChannel = body()
        fileSystem.sink(outputFile).buffer().use {
            byteReadChannel.readFully(it)
        }

        success = fileSystem.exists(outputFile)
    } catch (e: IOException) {
        Logger.e(e) { "Failed to save response stream to [${outputFile.name}]" }
        fileSystem.delete(outputFile)
    }
    return success
}

// Okio likes to use 8kb:
// https://github.com/square/okio/blob/a94c678de4e8a21e53126d42a1a3d897daa56a4a/recipes/index.html#L1322
private const val OKIO_RECOMMENDED_BUFFER_SIZE: Int = 8192

// https://youtrack.jetbrains.com/issue/KTOR-5066/Integration-with-okio
private suspend fun ByteReadChannel.readFully(sink: BufferedSink) {
    val channel = this
    while (!channel.isClosedForRead) {
        // TODO: Allocating a new packet on every copy isn't great. Find a faster way to move bytes.
        val packet = channel.readRemaining(OKIO_RECOMMENDED_BUFFER_SIZE.toLong())
        while (!packet.isEmpty) {
            sink.write(packet.readBytes())
        }
    }
}

private suspend fun ByteWriteChannel.writeAll(source: BufferedSource) {
    val channel = this
    var bytesRead: Int
    val buffer = ByteArray(OKIO_RECOMMENDED_BUFFER_SIZE)

    while (source.read(buffer).also { bytesRead = it } != -1 && !channel.isClosedForWrite) {
        channel.writeFully(buffer, startIndex = 0, endIndex = bytesRead)
        channel.flush()
    }
}

fun HttpRequestBuilder.cacheHeaders(etag: String?, lastModified: String?) {
    if (!etag.isNullOrBlank()) {
        ifNoneMatch(etag)
    }
    if (!lastModified.isNullOrBlank()) {
        header(HttpHeaders.IfModifiedSince, lastModified)
    }
}

fun DefaultRequest.DefaultRequestBuilder.acceptLanguage(application: Application) {
    val locales = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        application.resources.configuration.locales.toLanguageTags()
    } else {
        @Suppress("DEPRECATION")
        application.resources.configuration.locale.toLanguageTag()
    }
    header(HttpHeaders.AcceptLanguage, locales)
}
