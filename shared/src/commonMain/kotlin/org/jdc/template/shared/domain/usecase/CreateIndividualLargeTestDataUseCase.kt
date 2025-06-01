package org.jdc.template.shared.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jdc.template.shared.model.domain.Individual
import org.jdc.template.shared.model.domain.inline.FirstName
import org.jdc.template.shared.model.domain.inline.LastName
import org.jdc.template.shared.model.domain.inline.Phone
import org.jdc.template.shared.model.domain.type.IndividualType
import org.jdc.template.shared.model.repository.IndividualRepository

class CreateIndividualLargeTestDataUseCase(
    private val individualRepository: IndividualRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() = withContext(defaultDispatcher) {
        // clear any existing items
        individualRepository.deleteAllIndividuals()

        val individualList = mutableListOf<Individual>()
        for (i in 1..25) {
            individualList.add(Individual(
                firstName = FirstName("Person"),
                lastName = LastName("$i"),
                phone = Phone("801-555-00$i"),
                individualType = IndividualType.HEAD,
                birthDate = LocalDate(1970, 1, 1),
                alarmTime = LocalTime(7, 0),
            ))
        }

        individualRepository.saveIndividuals(individualList)
    }
}
