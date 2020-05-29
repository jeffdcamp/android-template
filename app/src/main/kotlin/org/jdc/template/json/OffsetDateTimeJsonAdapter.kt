package org.jdc.template.json

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PrimitiveDescriptor
import kotlinx.serialization.PrimitiveKind
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializer
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Serializer(forClass = OffsetDateTime::class)
object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveDescriptor("OffsetDateTimeSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value))
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return OffsetDateTime.parse(decoder.decodeString())
    }
}

@Serializer(forClass = Instant::class)
object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveDescriptor("InstantSerializer", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeLong(value.toEpochMilli())
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.ofEpochMilli(decoder.decodeLong())
    }
}

@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveDescriptor("LocalDateTimeSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(DateTimeFormatter.ISO_DATE_TIME.format(value))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString())
    }
}

@Serializer(forClass = LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveDescriptor("LocalDateSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(DateTimeFormatter.ISO_DATE.format(value))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }
}

@Serializer(forClass = LocalTime::class)
object LocalTimeSerializer : KSerializer<LocalTime> {
    override val descriptor: SerialDescriptor = PrimitiveDescriptor("LocalTimeSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalTime) {
        encoder.encodeString(DateTimeFormatter.ISO_TIME.format(value))
    }

    override fun deserialize(decoder: Decoder): LocalTime {
        return LocalTime.parse(decoder.decodeString())
    }
}