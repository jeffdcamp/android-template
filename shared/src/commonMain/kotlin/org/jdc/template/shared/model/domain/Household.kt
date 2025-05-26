package org.jdc.template.shared.model.domain

import kotlinx.datetime.Clock
import org.jdc.template.shared.model.domain.inline.CreatedTime
import org.jdc.template.shared.model.domain.inline.HouseholdId
import org.jdc.template.shared.model.domain.inline.LastModifiedTime
import org.jdc.template.shared.model.domain.inline.LastName
import java.util.UUID

data class Household(
    val id: HouseholdId = HouseholdId(UUID.randomUUID().toString()),
    val name: LastName,

    val created: CreatedTime = CreatedTime(Clock.System.now()),
    val lastModified: LastModifiedTime = LastModifiedTime(Clock.System.now())
)