package org.jdc.template.domain.individual

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jdc.template.model.domain.Household
import org.jdc.template.model.domain.Individual
import org.jdc.template.model.domain.inline.FirstName
import org.jdc.template.model.domain.inline.LastName
import org.jdc.template.model.domain.inline.Phone
import org.jdc.template.model.domain.type.IndividualType
import org.jdc.template.model.repository.IndividualRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class CreateIndividualTestDataUseCase
@Inject constructor(
    private val individualRepository: IndividualRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.Default) {
        // clear any existing items
        individualRepository.deleteAllIndividuals()

        val household1 = Household(
            name = LastName("Campbell")
        )

        val individual1 = Individual(
            householdId = household1.id,
            firstName = FirstName("Jeff"),
            lastName = LastName("Campbell"),
            phone = Phone("801-555-0000"),
            individualType = IndividualType.HEAD,
            birthDate = LocalDate.of(1970, 1, 1),
            alarmTime = LocalTime.of(7, 0),
        )

        val individual1a = Individual(
            householdId = household1.id,
            firstName = FirstName("Ty"),
            lastName = LastName("Campbell"),
            phone = Phone("801-555-0001"),
            individualType = IndividualType.CHILD,
            birthDate = LocalDate.of(1970, 1, 1),
            alarmTime = LocalTime.of(7, 0),
        )

        val household2 = Household(
            name = LastName("Miller")
        )

        val individual2 = Individual(
            householdId = household2.id,
            firstName = FirstName("John"),
            lastName = LastName("Miller"),
            phone = Phone("303-555-1111"),
            individualType = IndividualType.HEAD,
            birthDate = LocalDate.of(1970, 1, 2),
            alarmTime = LocalTime.of(6, 0),
        )

        individualRepository.saveNewHousehold(household1, listOf(individual1, individual1a))
        individualRepository.saveNewHousehold(household2, listOf(individual2))
    }
}
