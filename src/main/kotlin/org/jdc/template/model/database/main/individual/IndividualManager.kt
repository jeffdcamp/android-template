/*
 * IndividualManager.kt
 *
 * Generated on: 02/14/2016 07:02:10
 *
 */



package org.jdc.template.model.database.main.individual

import org.jdc.template.model.database.DatabaseManager
import org.threeten.bp.LocalDateTime
import javax.inject.Inject


@javax.inject.Singleton
class IndividualManager @Inject constructor(databaseManager: DatabaseManager) : IndividualBaseManager(databaseManager) {


    override fun save(individual: Individual?, databaseName: String): Boolean {
        if (individual == null) {
            return false
        }

        if (individual.birthDate != null && individual.alarmTime != null) {
            individual.sampleDateTime = LocalDateTime.of(individual.birthDate, individual.alarmTime)
        }

        individual.lastModified = LocalDateTime.now()
        return super.save(individual, databaseName)
    }
}