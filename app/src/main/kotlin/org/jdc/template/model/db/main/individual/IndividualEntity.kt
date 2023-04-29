package org.jdc.template.model.db.main.individual

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jdc.template.model.domain.type.IndividualType
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime

@Entity("Individual")
data class IndividualEntity(
    @PrimaryKey
    val id: String,
    val householdId: String?,
    val individualType: IndividualType,
    val firstName: String?,
    val lastName: String?,
    val birthDate: LocalDate?,
    val alarmTime: LocalTime?,
    val phone: String?,
    val email: String?,
    val available: Boolean,

    val created: OffsetDateTime,
    val lastModified: OffsetDateTime
)
