package org.jdc.template.domain.individual

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jdc.template.model.db.main.household.Household
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.db.main.type.IndividualType
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

        val household1 = Household().apply {
            name = "Campbell"
        }

        val individual1 = Individual().apply {
            householdId = household1.id
            firstName = "Jeff"
            lastName = "Campbell"
            phone = "801-555-0000"
            individualType = IndividualType.HEAD
            birthDate = LocalDate.of(1970, 1, 1)
            alarmTime = LocalTime.of(7, 0)
        }

        val individual1a = Individual().apply {
            householdId = household1.id
            firstName = "Ty"
            lastName = "Campbell"
            phone = "801-555-0001"
            individualType = IndividualType.HEAD
            birthDate = LocalDate.of(1970, 1, 1)
            alarmTime = LocalTime.of(7, 0)
        }

        val household2 = Household().apply {
            name = "Miller"
        }

        val individual2 = Individual().apply {
            householdId = household2.id
            firstName = "John"
            lastName = "Miller"
            phone = "303-555-1111"
            individualType = IndividualType.CHILD
            birthDate = LocalDate.of(1970, 1, 2)
            alarmTime = LocalTime.of(6, 0)
        }

        individualRepository.saveNewHousehold(household1, listOf(individual1, individual1a))
        individualRepository.saveNewHousehold(household2, listOf(individual2))
    }
}
