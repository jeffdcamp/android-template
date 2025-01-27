package org.jdc.template.model.db.main.household

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import org.jdc.template.model.domain.inline.HouseholdId
import org.jdc.template.model.domain.inline.LastName

@Entity(tableName = "Household")
data class HouseholdEntity(
    @PrimaryKey
    val id: HouseholdId,
    val name: LastName,

    val created: Instant,
    val lastModified: Instant
)
