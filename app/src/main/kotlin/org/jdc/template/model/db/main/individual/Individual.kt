package org.jdc.template.model.db.main.individual

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jdc.template.model.db.main.type.IndividualType
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime

@Entity
data class Individual(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var householdId: Long = 0,
    var individualType: IndividualType = IndividualType.HEAD,
    var firstName: String = "",
    var lastName: String = "",
    var birthDate: LocalDate? = null,
    var alarmTime: LocalTime = LocalTime.now(),
    var lastModified: OffsetDateTime = OffsetDateTime.now(),
    var phone: String = "",
    var email: String = "",
    var available: Boolean = false
) {

    fun getFullName() = "$firstName $lastName"
}
