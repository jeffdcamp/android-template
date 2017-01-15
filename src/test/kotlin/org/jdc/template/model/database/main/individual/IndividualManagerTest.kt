package org.jdc.template.model.database.main.individual

import org.dbtools.android.domain.config.DatabaseConfig
import org.jdc.template.log.JavaTree
import org.jdc.template.model.database.TestMainDatabaseConfig
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalTime
import timber.log.Timber
import javax.inject.Inject

class IndividualManagerTest {
    @Inject
    internal lateinit var databaseConfig: DatabaseConfig
    @Inject
    internal lateinit var individualManager: IndividualManager

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val component = DaggerIndividualManagerTestComponent.builder().individualManagerTestModule(IndividualManagerTestModule()).build()
        component.inject(this)

        Timber.plant(JavaTree()) // JVM Tree is

        (databaseConfig as TestMainDatabaseConfig).deleteAllDatabaseFiles()
    }

    @Test
    fun testIndividual() {
        Timber.e("Blah")

        // === CREATE / INSERT ===
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
