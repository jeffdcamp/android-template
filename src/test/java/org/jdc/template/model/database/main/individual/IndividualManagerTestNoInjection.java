package org.jdc.template.model.database.main.individual;

import org.jdc.template.model.database.DatabaseManager;
import org.jdc.template.model.database.TestMainDatabaseConfig;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalTime;

import static org.junit.Assert.assertEquals;

public class IndividualManagerTestNoInjection {

    private IndividualManager individualManager;

    @Before
    public void setUp() throws Exception {
        TestMainDatabaseConfig databaseConfig = TestMainDatabaseConfig.getInstance();
        databaseConfig.deleteAllDatabaseFiles();
        DatabaseManager databaseManager = new DatabaseManager(databaseConfig);
        individualManager = new IndividualManager(databaseManager);
    }

    @Test
    public void testIndividual() {
        // === CREATE / INSERT ===
        Individual individual = new Individual();
        individual.setFirstName("Jeff");
        individual.setAlarmTime(LocalTime.now());
        individualManager.save(individual);

        assertEquals(1, individualManager.findCount());

        // === UPDATE ===
        individual.setFirstName("Jeffery");
        individualManager.save(individual);

        String dbFirstName = individualManager.findValueBySelection(String.class, IndividualConst.C_FIRST_NAME, IndividualConst.C_ID + " = " + individual.getId(), null, "");
        assertEquals("Jeffery", dbFirstName);

        // === DELETE ===
        individualManager.delete(individual);
        assertEquals(0, individualManager.findCount());
    }
}