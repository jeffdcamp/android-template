package org.jdc.template.model.database.main.individual;

import org.dbtools.android.domain.config.DatabaseConfig;
import org.jdc.template.log.JavaTree;
import org.jdc.template.model.database.TestMainDatabaseConfig;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalTime;

import javax.inject.Inject;

import timber.log.Timber;

import static org.junit.Assert.assertEquals;

public class IndividualManagerTest {

    @Inject
    DatabaseConfig databaseConfig;
    @Inject
    IndividualManager individualManager;

    @Before
    public void setUp() throws Exception {
        IndividualManagerTestComponent component = DaggerIndividualManagerTestComponent.builder().individualManagerTestModule(new IndividualManagerTestModule()).build();
        component.inject(this);

        Timber.plant(new JavaTree()); // JVM Tree is

        ((TestMainDatabaseConfig) databaseConfig).deleteAllDatabaseFiles();
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