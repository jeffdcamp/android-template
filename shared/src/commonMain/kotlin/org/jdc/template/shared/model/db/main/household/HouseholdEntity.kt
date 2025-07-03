package org.jdc.template.shared.model.db.main.household

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant
import org.jdc.template.shared.model.domain.inline.HouseholdId
import org.jdc.template.shared.model.domain.inline.LastName

@Entity(tableName = "Household")
data class HouseholdEntity(
    @PrimaryKey
    val id: HouseholdId,
    val name: LastName,

    val created: Instant,
    val lastModified: Instant
)
