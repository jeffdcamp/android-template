@file:Suppress("unused")

package org.jdc.template.util.ext

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
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

suspend fun <T: Any> HttpClient.executeSafely(
    apiCall: suspend HttpClient.() -> HttpResponse,
    onError: suspend (HttpResponse) -> Unit = { Logger.w { "Error executing service call: ${it.request.method.value} ${it.request.url} (${it.status})" } },
    onException: suspend (IOException) -> Unit = { Logger.e(it) { "Exception executing service call" } },
    mapSuccess: suspend (HttpResponse) -> T,
): ApiResponse<T> {
    return try {
        val response = apiCall()
        if (response.status.isSuccess()) {
            ApiResponse.Success(mapSuccess(response))
        } else {
            onError(response)
            ApiResponse.Error()
        }
    } catch (e: IOException) {
        onException(e)
        ApiResponse.Error()
    }
}

sealed interface ApiResponse<T> {
    data class Success<T: Any>(val value: T) : ApiResponse<T>
    class Error<T> : ApiResponse<T>
}

/**
 * Write Ktor HttpResponse stream to file
 *
 * @param fileSystem Filesystem to save the file outputFile to
 * @param outputFile target file.  If the file already exists, then remove the file
 * @return true if the file streamed successfully to file and the new file exists
 */
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
