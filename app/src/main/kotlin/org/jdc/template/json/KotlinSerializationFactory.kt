package org.jdc.template.json

/*import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.serializer
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class KotlinSerializationFactory(
    private val contentType: MediaType,
    private val serializer: KotlinSerializationSerializer
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

internal sealed class KotlinSerializationSerializer {
    abstract fun <T> fromResponseBody(loader: DeserializationStrategy<T>, body: ResponseBody): T
    abstract fun <T> toRequestBody(contentType: MediaType, saver: SerializationStrategy<T>, value: T): RequestBody

    class FromString(private val format: StringFormat) : KotlinSerializationSerializer() {
        override fun <T> fromResponseBody(loader: DeserializationStrategy<T>, body: ResponseBody): T {
            val string = body.string()
            return format.decodeFromString(loader, string)
        }

        override fun <T> toRequestBody(contentType: MediaType, saver: SerializationStrategy<T>, value: T): RequestBody {
            val string = format.encodeToString(saver, value)
            return string.toRequestBody(contentType)
        }
    }

    class FromBytes(private val format: BinaryFormat): KotlinSerializationSerializer() {
        override fun <T> fromResponseBody(loader: DeserializationStrategy<T>, body: ResponseBody): T {
            val bytes = body.bytes()
            return format.decodeFromByteArray(loader, bytes)
        }

        override fun <T> toRequestBody(contentType: MediaType, saver: SerializationStrategy<T>, value: T): RequestBody {
            val content = format.encodeToByteArray(saver, value)
            return content.toRequestBody(contentType, 0, content.size)
        }
    }
}

internal class DeserializationStrategyConverter<T>(
    private val loader: DeserializationStrategy<T>,
    private val serializer: KotlinSerializationSerializer
) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody) = serializer.fromResponseBody(loader, value)
}

internal class SerializationStrategyConverter<T>(
    private val contentType: MediaType,
    private val saver: SerializationStrategy<T>,
    private val serializer: KotlinSerializationSerializer
) : Converter<T, RequestBody> {
    override fun convert(value: T) = serializer.toRequestBody(contentType, saver, value)
}

*//**
 * Return a [Converter.Factory] which uses Kotlin serialization for string-based payloads.
 *
 * Because Kotlin serialization is so flexible in the types it supports, this converter assumes
 * that it can handle all types. If you are mixing this with something else, you must add this
 * instance last to allow the other converters a chance to see their types.
 *//*
fun StringFormat.asConverterFactory(contentType: MediaType): Converter.Factory {
    return KotlinSerializationFactory(contentType, KotlinSerializationSerializer.FromString(this))
}

*//**
 * Return a [Converter.Factory] which uses Kotlin serialization for byte-based payloads.
 *
 * Because Kotlin serialization is so flexible in the types it supports, this converter assumes
 * that it can handle all types. If you are mixing this with something else, you must add this
 * instance last to allow the other converters a chance to see their types.
 *//*
fun BinaryFormat.asConverterFactory(contentType: MediaType): Converter.Factory {
    return KotlinSerializationFactory(contentType, KotlinSerializationSerializer.FromBytes(this))
}*/