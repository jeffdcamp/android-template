package org.jdc.template.ux.individual

import kotlinx.coroutines.experimental.runBlocking
import org.dbtools.android.domain.config.DatabaseConfig
import org.jdc.template.MockitoKotlinHelper
import org.jdc.template.log.JavaTree
import org.jdc.template.model.database.TestMainDatabaseConfig
import org.jdc.template.model.database.main.individual.Individual
import org.jdc.template.model.database.main.individual.IndividualManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import timber.log.Timber
import javax.inject.Inject

class IndividualPresenterTest {

    @Inject
    lateinit var databaseConfig: DatabaseConfig
    @Inject
    lateinit var individualPresenter: IndividualPresenter
    @Inject
    lateinit var individualManager: IndividualManager

    @Before
    @Throws(Exception::class)
    fun setUp() {
        DaggerIndividualPresenterTestComponent
                .builder()
                .build()
                .inject(this)

        // delete any existing databases
        (databaseConfig as TestMainDatabaseConfig).deleteAllDatabaseFiles()

        Timber.plant(JavaTree()) // JVM Tree is
    }

    @Test
    @Throws(Exception::class)
    fun testLoad() = runBlocking {
        // create an individual
        val ind1 = Individual()
        ind1.firstName = "Jeff"
        individualManager.save(ind1)

        val individualView = mock(IndividualContract.View::class.java)

        individualPresenter.init(individualView, ind1.id)

        // start the presenter
        individualPresenter.load()?.join() // join to force wait

        // make sure "showIndividual" is called
        verify(individualView, times(1)).showIndividual(MockitoKotlinHelper.any())
    }
}