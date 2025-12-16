package org.jdc.template.shared.model.domain.inline

import kotlinx.serialization.Serializable
import org.jdc.template.shared.util.serialization.DataClassInstantSerializer
import org.jdc.template.shared.util.serialization.DataClassStringSerializer
import org.jdc.template.shared.util.serialization.DataValueInstantClass
import org.jdc.template.shared.util.serialization.DataValueStringClass
import kotlin.time.Instant

@Serializable(with = IndividualIdSerializer::class)
data class IndividualId(override val value: String): DataValueStringClass {
    init {
        require(value.isNotBlank()) { "IndividualId cannot have a empty value" }
    }
}
object IndividualIdSerializer : DataClassStringSerializer<IndividualId>("IndividualIdSerializer", { IndividualId(it) })

@Serializable(with = HouseholdIdSerializer::class)
data class HouseholdId(override val value: String): DataValueStringClass {
    init {
        require(value.isNotBlank()) { "HouseholdId cannot have a empty value" }
    }
}
object HouseholdIdSerializer : DataClassStringSerializer<HouseholdId>("HouseholdIdSerializer", { HouseholdId(it) })

@Serializable(with = FirstNameSerializer::class)
data class FirstName(override val value: String): DataValueStringClass
object FirstNameSerializer : DataClassStringSerializer<FirstName>("FirstNameSerializer", { FirstName(it) })

@Serializable(with = LastNameSerializer::class)
data class LastName(override val value: String): DataValueStringClass
object LastNameSerializer : DataClassStringSerializer<LastName>("LastNameSerializer", { LastName(it) })

@Serializable(with = PhoneSerializer::class)
data class Phone(override val value: String): DataValueStringClass
object PhoneSerializer : DataClassStringSerializer<Phone>("PhoneSerializer", { Phone(it) })

@Serializable(with = EmailSerializer::class)
data class Email(override val value: String): DataValueStringClass
object EmailSerializer : DataClassStringSerializer<Email>("EmailSerializer", { Email(it) })

@Serializable(with = CreatedTimeSerializer::class)
data class CreatedTime(override val value: Instant): DataValueInstantClass
object CreatedTimeSerializer : DataClassInstantSerializer<CreatedTime>("CreatedTimeSerializer", { CreatedTime(it) })

@Serializable(with = LastModifiedTimeSerializer::class)
data class LastModifiedTime(override val value: Instant): DataValueInstantClass
object LastModifiedTimeSerializer : DataClassInstantSerializer<LastModifiedTime>("LastModifiedTimeSerializer", { LastModifiedTime(it) })

@Serializable(with = ChatThreadIdSerializer::class)
data class ChatThreadId(override val value: String): DataValueStringClass {
    init {
        require(value.isNotBlank()) { "ChatThreadId cannot have a empty value" }
    }
}
object ChatThreadIdSerializer : DataClassStringSerializer<ChatThreadId>("ChatThreadIdSerializer", { ChatThreadId(it) })

@Serializable(with = ChatMessageIdSerializer::class)
data class ChatMessageId(override val value: String): DataValueStringClass {
    init {
        require(value.isNotBlank()) { "ChatMessageId cannot have a empty value" }
    }
}
object ChatMessageIdSerializer : DataClassStringSerializer<ChatMessageId>("ChatMessageIdSerializer", { ChatMessageId(it) })
