package org.jdc.template.util.ext

import okio.FileSystem
import okio.Path
import okio.Source
import okio.buffer
import okio.source
import java.io.InputStream

fun Path.copyInputStreamToFile(inputStream: InputStream, fileSystem: FileSystem = FileSystem.SYSTEM) {
    inputStream.source().buffer().use { bufferedSource ->
        fileSystem.write(this) { writeAll(bufferedSource) }
    }
}

fun Path.copySourceToFile(source: Source, fileSystem: FileSystem = FileSystem.SYSTEM) {
    source.buffer().use { bufferedSource ->
        fileSystem.write(this) { writeAll(bufferedSource) }
    }
}

fun Path.isDirectory(fileSystem: FileSystem = FileSystem.SYSTEM) = fileSystem.metadataOrNull(this)?.isDirectory == true