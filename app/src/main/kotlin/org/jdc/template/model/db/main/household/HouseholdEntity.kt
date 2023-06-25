package org.jdc.template.model.db.main.household

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jdc.template.model.domain.inline.HouseholdId
import org.jdc.template.model.domain.inline.LastName
import java.time.OffsetDateTime

@Entity("Household")
data class HouseholdEntity(
    @PrimaryKey
    val id: HouseholdId,
    val name: LastName,

    val created: OffsetDateTime,
    val lastModified: OffsetDateTime
)
