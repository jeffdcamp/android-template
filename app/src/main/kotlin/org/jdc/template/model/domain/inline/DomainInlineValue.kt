package org.jdc.template.model.domain.inline

import kotlinx.datetime.Instant

@JvmInline
value class IndividualId(val value: String) {
    init {
        require(value.isNotBlank()) { "IndividualId cannot have a empty value" }
    }

    override fun toString(): String = value
}

@JvmInline
value class HouseholdId(val value: String) {
    init {
        require(value.isNotBlank()) { "HouseholdId cannot have a empty value" }
    }

    override fun toString(): String = value
}

@JvmInline
value class FirstName(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class LastName(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class Phone(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class Email(val value: String) {
    override fun toString(): String = value
}

@JvmInline
value class CreatedTime(val value: Instant)

@JvmInline
value class LastModifiedTime(val value: Instant)
