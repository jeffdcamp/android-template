@file:Suppress("unused")

package org.jdc.template.util.ext

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
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
import io.ktor.utils.io.errors.IOException
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path
import okio.buffer

@Suppress("kotlin:S6312") // Sonar issue with Coroutine scope on function ext
suspend fun <T, E> HttpClient.executeSafely(
    apiCall: suspend HttpClient.() -> HttpResponse,
    mapError: suspend (HttpResponse) -> ApiResponse.Failure.Error<E> = {
        ApiResponse.Failure.Error(
            null,
            "Error executing service call: ${it.request.method.value} ${it.request.url} (${it.status})"
        )
    },
    mapException: suspend (Throwable) -> ApiResponse.Failure.Exception = { ApiResponse.Failure.Exception(it) },
    mapSuccess: suspend (HttpResponse) -> T,
): ApiResponse<T, E> {
    return try {
        val response = apiCall()
        if (response.status.isSuccess()) {
            @Suppress("UNCHECKED_CAST", "kotlin:S6531") // This is unnecessary, but the compiler doesn't know that.
            ApiResponse.Success(mapSuccess(response)) as ApiResponse<T, E>
        } else {
            @Suppress("UNCHECKED_CAST") // This is unnecessary, but the compiler doesn't know that.
            mapError(response) as ApiResponse<T, E>
        }
    } catch (expected: Throwable) {
        @Suppress("UNCHECKED_CAST") // This is unnecessary, but the compiler doesn't know that.
        mapException(expected) as ApiResponse<T, E>
    }
}

@Suppress("kotlin:S6312") // Sonar issue with Coroutine scope on function ext
suspend fun <T, E> HttpClient.executeSafelyCached(
    apiCall: suspend HttpClient.() -> HttpResponse,
    mapError: suspend (HttpResponse) -> CacheApiResponse.Failure.Error<E> = {
        CacheApiResponse.Failure.Error(
            null,
            "Error executing service call: ${it.request.method.value} ${it.request.url} (${it.status})"
        )
    },
    mapException: suspend (Throwable) -> ApiResponse.Failure.Exception = { ApiResponse.Failure.Exception(it) },
    mapSuccess: suspend (HttpResponse) -> T,
): CacheApiResponse<T, E> {
    return try {
        val response = apiCall()
        if (response.status == HttpStatusCode.NotModified) {
            @Suppress("UNCHECKED_CAST", "kotlin:S6531") // This is unnecessary, but the compiler doesn't know that.
            CacheApiResponse.Success(null, response.etag(), response.headers[HttpHeaders.LastModified]) as CacheApiResponse<T, E>
        }
        if (response.status.isSuccess()) {
            @Suppress("UNCHECKED_CAST", "kotlin:S6531") // This is unnecessary, but the compiler doesn't know that.
            CacheApiResponse.Success(mapSuccess(response), response.etag(), response.headers[HttpHeaders.LastModified]) as CacheApiResponse<T, E>
        } else {
            @Suppress("UNCHECKED_CAST") // This is unnecessary, but the compiler doesn't know that.
            mapError(response) as CacheApiResponse<T, E>
        }
    } catch (expected: Throwable) {
        @Suppress("UNCHECKED_CAST") // This is unnecessary, but the compiler doesn't know that.
        mapException(expected) as CacheApiResponse<T, E>
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
        channel.writeFully(buffer, offset = 0, length = bytesRead)
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
