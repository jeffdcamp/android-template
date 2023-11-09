package org.jdc.template.ux

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.jdc.template.model.db.main.directoryitem.DirectoryItemEntityView
import org.jdc.template.model.domain.Individual
import org.jdc.template.model.domain.inline.FirstName
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.model.domain.inline.LastName
import org.jdc.template.model.repository.IndividualRepository

fun mockIndividualRepository(): IndividualRepository {
    val mockIndividualRepository = mockk<IndividualRepository>()

    coEvery { mockIndividualRepository.getDirectoryListFlow() } returns flowOf(TestIndividuals.directoryItems)

    coEvery { mockIndividualRepository.getIndividual(TestIndividuals.individualId1) } returns TestIndividuals.individual1
    coEvery { mockIndividualRepository.getIndividualFlow(TestIndividuals.individualId1) } returns flowOf(TestIndividuals.individual1)
    coEvery { mockIndividualRepository.deleteIndividual(any<IndividualId>()) } coAnswers {
        val individualId = IndividualId(firstArg<String>()) // mockk does not maintain inline/value class
        TestIndividuals.directoryItems.removeAll { it.individualId == individualId }
    }

    return mockIndividualRepository
}

object TestIndividuals {
    val individualId1 = IndividualId("1")
    val individual1 = Individual(id = individualId1, firstName = FirstName("Jeff"), lastName = LastName("Campbell"))
    val individualId2 = IndividualId("2")
    val individual2 = Individual(id = individualId2, firstName = FirstName("Mark"), lastName = LastName("Brown"))

    val directoryItems = mutableListOf(
        DirectoryItemEntityView(individualId1, individual1.firstName, individual1.lastName),
        DirectoryItemEntityView(individualId2, individual2.firstName, individual2.lastName)
    )
}