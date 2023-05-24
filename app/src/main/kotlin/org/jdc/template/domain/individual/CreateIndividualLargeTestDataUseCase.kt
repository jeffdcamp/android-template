package org.jdc.template.domain.individual

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jdc.template.inject.DefaultDispatcher
import org.jdc.template.model.domain.Individual
import org.jdc.template.model.domain.inline.FirstName
import org.jdc.template.model.domain.inline.LastName
import org.jdc.template.model.domain.inline.Phone
import org.jdc.template.model.domain.type.IndividualType
import org.jdc.template.model.repository.IndividualRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class CreateIndividualLargeTestDataUseCase
@Inject constructor(
    private val individualRepository: IndividualRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
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
                birthDate = LocalDate.of(1970, 1, 1),
                alarmTime = LocalTime.of(7, 0),
            ))
        }

        individualRepository.saveIndividuals(individualList)
    }
}
