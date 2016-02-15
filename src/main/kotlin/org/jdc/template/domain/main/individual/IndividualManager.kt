/*
 * IndividualManager.kt
 *
 * Generated on: 02/14/2016 07:02:10
 *
 */



package org.jdc.template.domain.main.individual

import org.jdc.template.domain.DatabaseManager
import org.threeten.bp.LocalDateTime


@javax.inject.Singleton
class IndividualManager : IndividualBaseManager {


    @javax.inject.Inject
    constructor(databaseManager: DatabaseManager): super(databaseManager) {
    }

    override fun save(databaseName: String, individual: Individual?): Boolean {
        if (individual == null) {
            return false
        }

        if (individual.birthDate != null && individual.alarmTime != null) {
            individual.sampleDateTime = LocalDateTime.of(individual.birthDate, individual.alarmTime)
        }

        individual.lastModified = LocalDateTime.now()
        return super.save(databaseName, individual)
    }

}