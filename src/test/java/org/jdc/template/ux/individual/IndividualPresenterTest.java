package org.jdc.template.ux.individual;

import org.dbtools.android.domain.config.DatabaseConfig;
import org.jdc.template.log.JavaTree;
import org.jdc.template.model.database.TestMainDatabaseConfig;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import timber.log.Timber;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class IndividualPresenterTest {

    @Inject
    DatabaseConfig databaseConfig;
    @Inject
    IndividualPresenter individualPresenter;
    @Inject
    IndividualManager individualManager;

    @Before
    public void setUp() {
        DaggerIndividualPresenterTestComponent
                .builder()
                .build()
                .inject(this);

        // delete any existing databases
        ((TestMainDatabaseConfig)databaseConfig).deleteAllDatabaseFiles();

        Timber.plant(new JavaTree()); // JVM Tree is
    }

    @Test
    public void testLoad() {
        // create an individual
        Individual ind1 = new Individual();
        ind1.setFirstName("Jeff");
        individualManager.save(ind1);

        IndividualContract.View individualView = mock(IndividualContract.View.class);

        individualPresenter.init(individualView, ind1.getId());

        // start the presenter
        individualPresenter.load(); // join to force wait

        // make sure "showIndividual" is called
        verify(individualView, times(1)).showIndividual(any());
    }
}