package org.jdc.template.model.repository

import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext
import org.jdc.template.model.datastore.UserPreferenceDataSource
import org.jdc.template.model.db.main.MainDatabaseWrapper
import org.jdc.template.model.db.main.household.Household
import org.jdc.template.model.db.main.individual.Individual
import org.jdc.template.model.db.main.type.IndividualType
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IndividualRepository
@Inject constructor(
    private val mainDatabaseWrapper: MainDatabaseWrapper,
    private val userPreferenceDataSource: UserPreferenceDataSource
) {
    private fun mainDatabase() = mainDatabaseWrapper.getDatabase()

    private fun individualDao() = mainDatabase().individualDao
    private fun householdDao() = mainDatabase().householdDao
    private fun directoryItemDao() = mainDatabase().directoryItemDao

    fun getDirectoryListFlow() = userPreferenceDataSource.directorySortByLastNameFlow.flatMapLatest { byLastName ->
        when {
            byLastName -> directoryItemDao().findAllDirectItemsByLastNameFlow()
            else -> directoryItemDao().findAllDirectItemsByFirstNameFlow()
        }
    }
    suspend fun setDirectorySort(byLastName: Boolean) {
        userPreferenceDataSource.setDirectorySort(byLastName)
    }
    suspend fun getIndividual(individualId: String) = individualDao().findById(individualId)
    fun getIndividualFlow(individualId: String) = individualDao().findByIdFlow(individualId)
    suspend fun getAllIndividuals() = individualDao().findAll()
    suspend fun getIndividualCount() = individualDao().findCount()
    suspend fun getIndividualFirstName(individualId: String) = individualDao().findFirstName(individualId)

    suspend fun saveIndividual(individual: Individual) {
        individualDao().insert(individual)
    }

    private suspend fun saveHousehold(household: Household) {
        householdDao().insert(household)
    }

    suspend fun saveNewHousehold(household: Household, individuals: List<Individual>) {
        mainDatabase().withTransaction {
            saveHousehold(household)

            individuals.forEach { individual ->
                individual.householdId = household.id
                saveIndividual(individual)
            }
        }
    }

    suspend fun deleteIndividual(individualId: String) = individualDao().deleteById(individualId)

    suspend fun deleteAllIndividuals() {
        mainDatabase().withTransaction {
            individualDao().deleteAll()
            householdDao().deleteAll()
        }
    }

    suspend fun getAllMembers() = householdDao().findAllMembers()


    /**
     * Creates sample data WITH using injection
     */
    suspend fun createSampleData() = withContext(Dispatchers.IO) {
        // clear any existing items
        deleteAllIndividuals()

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

        saveNewHousehold(household1, listOf(individual1, individual1a))
        saveNewHousehold(household2, listOf(individual2))
    }
}