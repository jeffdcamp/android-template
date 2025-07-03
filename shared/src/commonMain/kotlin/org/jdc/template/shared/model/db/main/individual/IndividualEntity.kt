package org.jdc.template.shared.model.db.main.individual

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jdc.template.shared.model.domain.inline.Email
import org.jdc.template.shared.model.domain.inline.FirstName
import org.jdc.template.shared.model.domain.inline.HouseholdId
import org.jdc.template.shared.model.domain.inline.IndividualId
import org.jdc.template.shared.model.domain.inline.LastName
import org.jdc.template.shared.model.domain.inline.Phone
import org.jdc.template.shared.model.domain.type.IndividualType

@Entity(tableName = "Individual")
data class IndividualEntity(
    @PrimaryKey
    val id: IndividualId,
    val householdId: HouseholdId?,
    val individualType: IndividualType,
    val firstName: FirstName?,
    val lastName: LastName?,
    val birthDate: LocalDate?,
    val alarmTime: LocalTime?,
    val phone: Phone?,
    val email: Email?,
    val available: Boolean,

    val created: Instant,
    val lastModified: Instant
)
