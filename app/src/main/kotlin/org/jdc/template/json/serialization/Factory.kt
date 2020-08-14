/**
 * Copied from Kotlin Serialization Converter (https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter)
 * Fixed to work with Kotlin 1.4.0 and kotlinx-serialization-runtime:1.0-M1-1.4.0-rc
 * Workaround for https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter/pull/26
 *
 * Copyright 2018 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:JvmName("KotlinSerializationConverterFactory")
package org.jdc.template.json.serialization

import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.StringFormat
import kotlinx.serialization.serializer
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class Factory(
    private val contentType: MediaType,
    private val serializer: Serializer
) : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type, annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val loader = serializer(type)
        return DeserializationStrategyConverter(loader, serializer)
    }

    override fun requestBodyConverter(
        type: Type, parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>, retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val saver = serializer(type)
        return SerializationStrategyConverter(contentType, saver, serializer)
    }
}

/**
 * Return a [Converter.Factory] which uses Kotlin serialization for string-based payloads.
 *
 * Because Kotlin serialization is so flexible in the types it supports, this converter assumes
 * that it can handle all types. If you are mixing this with something else, you must add this
 * instance last to allow the other converters a chance to see their types.
 */
@JvmName("create")
fun StringFormat.asConverterFactory(contentType: MediaType): Converter.Factory {
    return Factory(contentType, Serializer.FromString(this))
}

/**
 * Return a [Converter.Factory] which uses Kotlin serialization for byte-based payloads.
 *
 * Because Kotlin serialization is so flexible in the types it supports, this converter assumes
 * that it can handle all types. If you are mixing this with something else, you must add this
 * instance last to allow the other converters a chance to see their types.
 */
@JvmName("create")
fun BinaryFormat.asConverterFactory(contentType: MediaType): Converter.Factory {
    return Factory(contentType, Serializer.FromBytes(this))
}