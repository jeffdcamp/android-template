package org.jdc.template.domain.individual

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.db.main.type.IndividualType
import org.jdc.template.model.repository.IndividualRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class CreateIndividualLargeTestDataUseCase
@Inject constructor(
    private val individualRepository: IndividualRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.Default) {
        // clear any existing items
        individualRepository.deleteAllIndividuals()

        val individualList = mutableListOf<Individual>()
        for (i in 1..25) {
            individualList.add(Individual().apply {
                firstName = "Person"
                lastName = "$i"
                phone = "801-555-00$i"
                individualType = IndividualType.HEAD
                birthDate = LocalDate.of(1970, 1, 1)
                alarmTime = LocalTime.of(7, 0)
            })
        }

        individualRepository.saveIndividuals(individualList)
    }
}
