package org.jdc.template.model.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jdc.template.model.domain.inline.CreatedTime
import org.jdc.template.model.domain.inline.Email
import org.jdc.template.model.domain.inline.FirstName
import org.jdc.template.model.domain.inline.HouseholdId
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.model.domain.inline.LastModifiedTime
import org.jdc.template.model.domain.inline.LastName
import org.jdc.template.model.domain.inline.Phone
import org.jdc.template.model.domain.type.IndividualType
import java.util.UUID

data class Individual(
    val id: IndividualId = IndividualId(UUID.randomUUID().toString()),
    val householdId: HouseholdId? = null,

    val individualType: IndividualType = IndividualType.HEAD,
    val firstName: FirstName? = null,
    val lastName: LastName? = null,
    val birthDate: LocalDate? = null,
    val alarmTime: LocalTime? = null,
    val phone: Phone? = null,
    val email: Email? = null,
    val available: Boolean = false,

    val created: CreatedTime = CreatedTime(Clock.System.now()),
    val lastModified: LastModifiedTime = LastModifiedTime(Clock.System.now())
) {
    fun getFullName(): String = "${firstName?.value.orEmpty()} ${lastName?.value.orEmpty()}"
}
