package org.jdc.template.model.repository

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Component
import dagger.Module
import dagger.Provides
import org.dbtools.android.room.jdbc.JdbcSQLiteOpenHelperFactory
import org.jdc.template.TestFilesystem
import org.jdc.template.inject.CommonTestModule
import org.jdc.template.log.JavaTree
import org.jdc.template.model.db.main.MainDatabase
import org.jdc.template.model.db.main.individual.Individual
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.threeten.bp.LocalTime
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

class IndividualRepositoryTest {

    @Inject
    lateinit var individualRepository: IndividualRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Timber.plant(JavaTree())
        TestFilesystem.deleteFilesystem()

        val component = DaggerIndividualRepositoryTestComponent.builder().individualRepositoryTestModule(IndividualRepositoryTestModule()).build()
        component.inject(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testIndividual() {
        // === CREATE / INSERT ===
        val individual = Individual()
        individual.firstName = "Jeff"
        individual.alarmTime = LocalTime.now()
        individualRepository.saveIndividual(individual)

        assertEquals(1, individualRepository.getIndividualCount())

        // === UPDATE ===
        individual.firstName = "Jeffery"
        individualRepository.saveIndividual(individual)

        val dbFirstName = individualRepository.getIndividualFirstName(individual.id)
        assertEquals("Jeffery", dbFirstName)

        // === DELETE ===
        individualRepository.deleteIndividual(individual.id)
        assertEquals(0, individualRepository.getIndividualCount())
    }
}

@Module
class IndividualRepositoryTestModule {
    @Provides
    @Singleton
    fun provideMainDatabase(): MainDatabase {
        return Room.databaseBuilder(mock(Application::class.java), MainDatabase::class.java, MainDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .openHelperFactory(JdbcSQLiteOpenHelperFactory(TestFilesystem.INTERNAL_DATABASES_DIR_PATH))
            .build()
    }
}

@Singleton
@Component(modules = [CommonTestModule::class, IndividualRepositoryTestModule::class])
interface IndividualRepositoryTestComponent {
    fun inject(test: IndividualRepositoryTest)
}
