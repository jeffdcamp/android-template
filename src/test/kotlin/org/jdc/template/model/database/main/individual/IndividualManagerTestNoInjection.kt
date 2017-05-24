package org.jdc.template.model.database.main.individual

class IndividualManagerTestNoInjection {
//    @Test
//    fun testIndividual() {
//        val databaseConfig = TestMainDatabaseConfig.instance
//        databaseConfig.deleteAllDatabaseFiles()
//        val databaseManager = DatabaseManager(databaseConfig)
//
//        // === CREATE / INSERT ===
//        val individualManager = IndividualManager(databaseManager)
//        val individual = Individual()
//        individual.firstName = "Jeff"
//        individual.alarmTime = LocalTime.now()
//        individualManager.save(individual)
//
//        assertEquals(1, individualManager.findCount())
//
//        // === UPDATE ===
//        individual.firstName = "Jeffery"
//        individualManager.save(individual)
//
//        val dbFirstName = individualManager.findValueBySelection(String::class.java, column = IndividualConst.C_FIRST_NAME, selection = IndividualConst.C_ID + " = " + individual.id, defaultValue = "")
//        assertEquals("Jeffery", dbFirstName)
//
//        // === DELETE ===
//        individualManager.delete(individual)
//        assertEquals(0, individualManager.findCount())
//    }
}
