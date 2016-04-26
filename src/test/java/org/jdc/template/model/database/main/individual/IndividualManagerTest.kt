package org.jdc.template.model.database.main.individual

import org.jdc.template.model.database.DatabaseManager
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalTime

class IndividualManagerTest {
    @Test
    fun testIndividual() {
        val databaseConfig = TestMainDatabaseConfig("test-individual.db")
        databaseConfig.deleteDatabaseFile()
        val databaseManager = DatabaseManager(databaseConfig)

        // === CREATE / INSERT ===
        val individualManager = IndividualManager(databaseManager)
        val individual = Individual()
        individual.firstName = "Jeff"
        individual.alarmTime = LocalTime.now()
        individualManager.save(individual)

        assertEquals(1, individualManager.findCount())

        // === UPDATE ===
        individual.firstName = "Jeffery"
        individualManager.save(individual)

        val dbFirstName = individualManager.findValueBySelection(String::class.java, IndividualConst.C_FIRST_NAME, IndividualConst.C_ID + " = " + individual.id, null, "")
        assertEquals("Jeffery", dbFirstName)

        // === DELETE ===
        individualManager.delete(individual)
        assertEquals(0, individualManager.findCount())
    }
}
