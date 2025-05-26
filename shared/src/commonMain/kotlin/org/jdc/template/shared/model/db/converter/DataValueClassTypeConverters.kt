package org.jdc.template.shared.model.db.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import org.jdc.template.shared.model.domain.inline.ChatMessageId
import org.jdc.template.shared.model.domain.inline.ChatThreadId
import org.jdc.template.shared.model.domain.inline.CreatedTime
import org.jdc.template.shared.model.domain.inline.Email
import org.jdc.template.shared.model.domain.inline.FirstName
import org.jdc.template.shared.model.domain.inline.HouseholdId
import org.jdc.template.shared.model.domain.inline.IndividualId
import org.jdc.template.shared.model.domain.inline.LastModifiedTime
import org.jdc.template.shared.model.domain.inline.LastName
import org.jdc.template.shared.model.domain.inline.Phone

@Suppress("TooManyFunctions")
object DataValueClassTypeConverters {
    @TypeConverter
    fun fromStringToIndividualId(value: String): IndividualId = IndividualId(value)
    @TypeConverter
    fun fromIndividualIdToString(value: IndividualId): String = value.value

    @TypeConverter
    fun fromStringToHouseholdId(value: String): HouseholdId = HouseholdId(value)
    @TypeConverter
    fun fromHouseholdIdToString(value: HouseholdId): String = value.value

    @TypeConverter
    fun fromStringToFirstName(value: String): FirstName = FirstName(value)
    @TypeConverter
    fun fromFirstNameToString(value: FirstName): String = value.value

    @TypeConverter
    fun fromStringToLastName(value: String): LastName = LastName(value)
    @TypeConverter
    fun fromLastNameToString(value: LastName): String = value.value

    @TypeConverter
    fun fromStringToPhone(value: String): Phone = Phone(value)
    @TypeConverter
    fun fromPhoneToString(value: Phone): String = value.value

    @TypeConverter
    fun fromStringToEmail(value: String): Email = Email(value)
    @TypeConverter
    fun fromEmailToString(value: Email): String = value.value

    @TypeConverter
    fun fromStringToCreatedTime(value: String): CreatedTime = CreatedTime(Instant.parse(value))
    @TypeConverter
    fun fromCreatedTimeToString(value: CreatedTime): String = value.value.toString()

    @TypeConverter
    fun fromStringToLastModifiedTime(value: String): LastModifiedTime = LastModifiedTime(Instant.parse(value))
    @TypeConverter
    fun fromLastModifiedTimeToString(value: LastModifiedTime): String = value.value.toString()

    @TypeConverter
    fun fromStringToChatThreadId(value: String): ChatThreadId = ChatThreadId(value)
    @TypeConverter
    fun fromChatThreadIdToString(value: ChatThreadId): String = value.value

    @TypeConverter
    fun fromStringToChatMessageId(value: String): ChatMessageId = ChatMessageId(value)
    @TypeConverter
    fun fromChatMessageIdToString(value: ChatMessageId): String = value.value
}
