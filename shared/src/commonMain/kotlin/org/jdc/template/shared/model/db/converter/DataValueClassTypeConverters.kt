package org.jdc.template.shared.model.db.converter

import androidx.room3.ColumnTypeConverter
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
    @ColumnTypeConverter
    fun fromStringToIndividualId(value: String?): IndividualId? = value?.let { IndividualId(it) }
    @ColumnTypeConverter
    fun fromIndividualIdToString(value: IndividualId?): String? = value?.value

    @ColumnTypeConverter
    fun fromStringToHouseholdId(value: String?): HouseholdId? = value?.let { HouseholdId(it) }
    @ColumnTypeConverter
    fun fromHouseholdIdToString(value: HouseholdId?): String? = value?.value

    @ColumnTypeConverter
    fun fromStringToFirstName(value: String?): FirstName? = value?.let { FirstName(it) }
    @ColumnTypeConverter
    fun fromFirstNameToString(value: FirstName?): String? = value?.value

    @ColumnTypeConverter
    fun fromStringToLastName(value: String?): LastName? = value?.let { LastName(it) }
    @ColumnTypeConverter
    fun fromLastNameToString(value: LastName?): String? = value?.value

    @ColumnTypeConverter
    fun fromStringToPhone(value: String?): Phone? = value?.let { Phone(it) }
    @ColumnTypeConverter
    fun fromPhoneToString(value: Phone?): String? = value?.value

    @ColumnTypeConverter
    fun fromStringToEmail(value: String?): Email? = value?.let { Email(it) }
    @ColumnTypeConverter
    fun fromEmailToString(value: Email?): String? = value?.value

    @ColumnTypeConverter
    fun fromStringToCreatedTime(value: String?): CreatedTime? = value?.let { CreatedTime(Instant.parse(it)) }
    @ColumnTypeConverter
    fun fromCreatedTimeToString(value: CreatedTime?): String? = value?.value?.toString()

    @ColumnTypeConverter
    fun fromStringToLastModifiedTime(value: String?): LastModifiedTime? = value?.let { LastModifiedTime(Instant.parse(it)) }
    @ColumnTypeConverter
    fun fromLastModifiedTimeToString(value: LastModifiedTime?): String? = value?.value?.toString()

    @ColumnTypeConverter
    fun fromStringToChatThreadId(value: String?): ChatThreadId? = value?.let { ChatThreadId(it) }
    @ColumnTypeConverter
    fun fromChatThreadIdToString(value: ChatThreadId?): String? = value?.value

    @ColumnTypeConverter
    fun fromStringToChatMessageId(value: String?): ChatMessageId? = value?.let { ChatMessageId(it) }
    @ColumnTypeConverter
    fun fromChatMessageIdToString(value: ChatMessageId?): String? = value?.value
}
