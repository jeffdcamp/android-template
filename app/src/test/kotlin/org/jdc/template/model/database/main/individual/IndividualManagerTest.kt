package org.jdc.template.model.database.main.individual

class IndividualManagerTest {
//    @Inject
//    internal lateinit var databaseConfig: DatabaseConfig
//    @Inject
//    internal lateinit var individualManager: IndividualManager
//
//    @Before
//    @Throws(Exception::class)
//    fun setUp() {
//        val component = DaggerIndividualManagerTestComponent.builder().individualManagerTestModule(IndividualManagerTestModule()).build()
//        component.inject(this)
//
//        Timber.plant(JavaTree()) // JVM Tree is
//
//        (databaseConfig as TestMainDatabaseConfig).deleteAllDatabaseFiles()
//    }
//
//    @Test
//    fun testIndividual() {
//        Timber.e("Blah")
//
//        // === CREATE / INSERT ===
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
//        val dbFirstName = individualManager.findValueBySelection(valueType = String::class.java, column = IndividualConst.C_FIRST_NAME, selection = "${IndividualConst.C_ID} = ${individual.id}", defaultValue = "")
//        assertEquals("Jeffery", dbFirstName)
//
//        // === DELETE ===
//        individualManager.delete(individual)
//        assertEquals(0, individualManager.findCount())
//    }
//
//    @Module
//    class IndividualManagerTestModule {
//        @Provides
//        @Singleton
//        internal fun provideDatabaseConfig(): DatabaseConfig {
//            return TestMainDatabaseConfig.instance
//        }
//    }
}
