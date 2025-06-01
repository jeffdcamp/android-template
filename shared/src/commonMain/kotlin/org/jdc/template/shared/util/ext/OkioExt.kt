package org.jdc.template.shared.util.ext

import okio.FileSystem
import okio.Path
import okio.Source
import okio.buffer
import okio.use

/**
 * Check if a Path is a Directory
 * @param path Okio Path
 * @return true if the given Path IS a Directory
 */
fun FileSystem.isDirectory(path: Path): Boolean = metadataOrNull(path)?.isDirectory == true

/**
 * Read a simple text file
 * @param path Okio Path
 * @return String of text read from the given Path
 */
fun FileSystem.readText(path: Path): String = read(path) { readUtf8() }

/**
 * Write a simple text file
 * @param path Okio Path
 * @param text Text to be written to the file
 */
fun FileSystem.writeText(path: Path, text: String) = write(path) { writeUtf8(text) }

/**
 * Write Okio source stream to file Path
 * @param source Okio Source
 * @param file Okio file Path
 */
fun FileSystem.copySourceToFile(source: Source, file: Path) {
    source.buffer().use { bufferedSource ->
        write(file) { writeAll(bufferedSource) }
    }
}

/**
 * Copy file from one FileSystem to other FileSystem
 * @param source Path to file in source FileSystem
 * @param targetFileSystem Target FileSystem
 * @param target Path to file in target FileSystem
 */
fun FileSystem.copyFileToFileSystem(source: Path, targetFileSystem: FileSystem, target: Path) {
    source(source).use { bytesIn ->
        targetFileSystem.sink(target).buffer().use { bytesOut ->
            bytesOut.writeAll(bytesIn)
        }
    }
}
