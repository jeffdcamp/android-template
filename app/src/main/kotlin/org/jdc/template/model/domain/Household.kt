package org.jdc.template.model.domain

import org.jdc.template.model.domain.inline.CreatedTime
import org.jdc.template.model.domain.inline.HouseholdId
import org.jdc.template.model.domain.inline.LastModifiedTime
import org.jdc.template.model.domain.inline.LastName
import java.time.OffsetDateTime
import java.util.UUID

data class Household(
    val id: HouseholdId = HouseholdId(UUID.randomUUID().toString()),
    val name: LastName,

    val created: CreatedTime = CreatedTime(OffsetDateTime.now()),
    val lastModified: LastModifiedTime = LastModifiedTime(OffsetDateTime.now())
)