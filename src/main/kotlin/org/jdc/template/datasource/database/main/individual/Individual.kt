package org.jdc.template.datasource.database.main.individual

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import org.jdc.template.datasource.database.converter.DateTimeLongConverter
import org.jdc.template.datasource.database.converter.DateTimeTextConverter
import org.jdc.template.datasource.database.main.type.IndividualType
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

@Entity(tableName = "individual")
class Individual {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var householdId: Long = 0
    var individualType: IndividualType = IndividualType.HEAD
    var firstName: String = ""
    var lastName: String = ""
    var birthDate: LocalDate? = null
    var alarmTime: LocalTime = LocalTime.now()
    @TypeConverters(DateTimeLongConverter::class)
    var lastModified: LocalDateTime = LocalDateTime.now()
    @TypeConverters(DateTimeTextConverter::class)
    var phone: String = ""
    var email: String = ""
    var available: Boolean = false

    fun  getFullName(): String {
        return firstName + " " + lastName
    }
}
