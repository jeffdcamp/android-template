package org.jdc.template.model.db.main.household

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity("Household")
data class HouseholdEntity(
    @PrimaryKey
    val id: String,
    val name: String,

    val created: OffsetDateTime,
    val lastModified: OffsetDateTime
)
