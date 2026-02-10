package org.jdc.template.shared.model.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalTime
import okio.FileSystem
import org.jdc.template.shared.di.getSharedKoinModules
import org.jdc.template.shared.model.domain.Individual
import org.jdc.template.shared.model.domain.inline.FirstName
import org.jdc.template.shared.util.file.AppFileSystem
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test

class IndividualRepositoryTest: KoinTest {

    val individualRepository: IndividualRepository by inject()

    @BeforeTest
    fun setUp() {
        // Delete all files, databases, prefs, etc
        AppFileSystem.getFilesDir().parent?.let { FileSystem.SYSTEM.deleteRecursively(it) }

        startKoin {
            modules(getSharedKoinModules())
        }
    }

    @Test
    fun testIndividual() = runTest {
        // === CREATE / INSERT ===
        val individual = Individual(
            firstName = FirstName("Jeff"),
            alarmTime = LocalTime(10, 30)
        )
        individualRepository.saveIndividual(individual)

        assertThat(individualRepository.getIndividualCount()).isEqualTo(1)

        // === UPDATE ===
        individualRepository.saveIndividual(individual.copy(firstName = FirstName("Jeffery")))

        val dbFirstName = individualRepository.getIndividualFirstName(individual.id)
        assertThat(dbFirstName).isEqualTo("Jeffery")

        // === DELETE ===
        individualRepository.deleteIndividual(individual.id)
        assertThat(individualRepository.getIndividualCount()).isEqualTo(0)
    }
}

