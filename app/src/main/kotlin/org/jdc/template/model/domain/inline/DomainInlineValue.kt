package org.jdc.template.model.domain.inline

import java.time.OffsetDateTime

@JvmInline
value class IndividualId(val value: String)

@JvmInline
value class HouseholdId(val value: String)

@JvmInline
value class FirstName(val value: String)

@JvmInline
value class LastName(val value: String)

@JvmInline
value class Phone(val value: String)

@JvmInline
value class Email(val value: String)

@JvmInline
value class CreatedTime(val value: OffsetDateTime)

@JvmInline
value class LastModifiedTime(val value: OffsetDateTime)
