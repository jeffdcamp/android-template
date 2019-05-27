package org.jdc.template.json

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializer
import kotlinx.serialization.internal.LongDescriptor
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.withName
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

@Serializer(forClass = OffsetDateTime::class)
object OffsetDateTimeSerializer: KSerializer<OffsetDateTime> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("OffsetDateTimeSerializer")

    override fun serialize(output: Encoder, obj: OffsetDateTime) {
        output.encodeString(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(obj))
    }

    override fun deserialize(input: Decoder): OffsetDateTime {
        return OffsetDateTime.parse(input.decodeString())
    }
}

@Serializer(forClass = Instant::class)
object InstantSerializer: KSerializer<Instant> {
    override val descriptor: SerialDescriptor = LongDescriptor.withName("InstantSerializer")

    override fun serialize(output: Encoder, obj: Instant) {
        output.encodeLong(obj.toEpochMilli())
    }

    override fun deserialize(input: Decoder): Instant {
        return Instant.ofEpochMilli(input.decodeLong())
    }
}

@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer: KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("LocalDateTimeSerializer")

    override fun serialize(output: Encoder, obj: LocalDateTime) {
        output.encodeString(DateTimeFormatter.ISO_DATE_TIME.format(obj))
    }

    override fun deserialize(input: Decoder): LocalDateTime {
        return LocalDateTime.parse(input.decodeString())
    }
}

@Serializer(forClass = LocalDate::class)
object LocalDateSerializer: KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("LocalDateSerializer")

    override fun serialize(output: Encoder, obj: LocalDate) {
        output.encodeString(DateTimeFormatter.ISO_DATE.format(obj))
    }

    override fun deserialize(input: Decoder): LocalDate {
        return LocalDate.parse(input.decodeString())
    }
}

@Serializer(forClass = LocalTime::class)
object LocalTimeSerializer: KSerializer<LocalTime> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("LocalTimeSerializer")

    override fun serialize(output: Encoder, obj: LocalTime) {
        output.encodeString(DateTimeFormatter.ISO_TIME.format(obj))
    }

    override fun deserialize(input: Decoder): LocalTime {
        return LocalTime.parse(input.decodeString())
    }
}