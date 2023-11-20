package org.jdc.template.ux

import android.app.Application
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.jdc.template.model.db.main.directoryitem.DirectoryItemEntityView
import org.jdc.template.model.domain.Individual
import org.jdc.template.model.domain.inline.FirstName
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.model.domain.inline.LastName
import org.jdc.template.model.repository.IndividualRepository

fun mockApplication(): Application {
    val mockApplication = mockk<Application>()

    every { mockApplication.getString(any<Int>()) } coAnswers {
        val resId = firstArg<Int>()
        "application string: $resId"
    }

    return mockApplication
}

fun mockIndividualRepository(): IndividualRepository {
    val testIndividuals = TestIndividuals()

    val mockIndividualRepository = mockk<IndividualRepository>()

    coEvery { mockIndividualRepository.getDirectoryListFlow() } returns flowOf(testIndividuals.directoryItems)

    coEvery { mockIndividualRepository.getIndividual(any<IndividualId>()) } coAnswers {
        val individualId = IndividualId(firstArg<String>()) // mockk does not maintain inline/value class
        testIndividuals.individuals.firstOrNull { it.id == individualId }
    }
    coEvery { mockIndividualRepository.getIndividualFlow(any<IndividualId>()) } coAnswers {
        val individualId = IndividualId(firstArg<String>()) // mockk does not maintain inline/value class
        flowOf(testIndividuals.individuals.firstOrNull { it.id == individualId })
    }
    coEvery { mockIndividualRepository.deleteIndividual(any<IndividualId>()) } coAnswers {
        val individualId = IndividualId(firstArg<String>()) // mockk does not maintain inline/value class
        testIndividuals.individuals.removeAll { it.id == individualId }
    }
    coEvery { mockIndividualRepository.saveIndividual(any<Individual>()) } coAnswers {
        val individual = firstArg<Individual>()
        testIndividuals.individuals.add(individual)
    }

    return mockIndividualRepository
}

class TestIndividuals {
    val individuals: MutableList<Individual> = mutableListOf(
        individual1,
        individual2,
    )

    val directoryItems: List<DirectoryItemEntityView>
        get() {
            return individuals.map { DirectoryItemEntityView(it.id, it.firstName, it.lastName) }
        }

    companion object {
        val individualId1 = IndividualId("1")
        val individual1 = Individual(id = individualId1, firstName = FirstName("Jeff"), lastName = LastName("Campbell"))
        val individualId2 = IndividualId("2")
        val individual2 = Individual(id = individualId2, firstName = FirstName("Mark"), lastName = LastName("Brown"))
    }
}