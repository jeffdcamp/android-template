package org.jdc.template.shared.model.db.converter

import androidx.room.TypeConverter
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
import kotlin.time.Instant

@Suppress("TooManyFunctions")
object DataValueClassTypeConverters {
    @TypeConverter
    fun fromStringToIndividualId(value: String?): IndividualId? = value?.let { IndividualId(it) }
    @TypeConverter
    fun fromIndividualIdToString(value: IndividualId?): String? = value?.value

    @TypeConverter
    fun fromStringToHouseholdId(value: String?): HouseholdId? = value?.let { HouseholdId(it) }
    @TypeConverter
    fun fromHouseholdIdToString(value: HouseholdId?): String? = value?.value

    @TypeConverter
    fun fromStringToFirstName(value: String?): FirstName? = value?.let { FirstName(it) }
    @TypeConverter
    fun fromFirstNameToString(value: FirstName?): String? = value?.value

    @TypeConverter
    fun fromStringToLastName(value: String?): LastName? = value?.let { LastName(it) }
    @TypeConverter
    fun fromLastNameToString(value: LastName?): String? = value?.value

    @TypeConverter
    fun fromStringToPhone(value: String?): Phone? = value?.let { Phone(it) }
    @TypeConverter
    fun fromPhoneToString(value: Phone?): String? = value?.value

    @TypeConverter
    fun fromStringToEmail(value: String?): Email? = value?.let { Email(it) }
    @TypeConverter
    fun fromEmailToString(value: Email?): String? = value?.value

    @TypeConverter
    fun fromStringToCreatedTime(value: String?): CreatedTime? = value?.let { CreatedTime(Instant.parse(it)) }
    @TypeConverter
    fun fromCreatedTimeToString(value: CreatedTime?): String? = value?.value?.toString()

    @TypeConverter
    fun fromStringToLastModifiedTime(value: String?): LastModifiedTime? = value?.let { LastModifiedTime(Instant.parse(it)) }
    @TypeConverter
    fun fromLastModifiedTimeToString(value: LastModifiedTime?): String? = value?.value?.toString()

    @TypeConverter
    fun fromStringToChatThreadId(value: String?): ChatThreadId? = value?.let { ChatThreadId(it) }
    @TypeConverter
    fun fromChatThreadIdToString(value: ChatThreadId?): String? = value?.value

    @TypeConverter
    fun fromStringToChatMessageId(value: String?): ChatMessageId? = value?.let { ChatMessageId(it) }
    @TypeConverter
    fun fromChatMessageIdToString(value: ChatMessageId?): String? = value?.value
}
