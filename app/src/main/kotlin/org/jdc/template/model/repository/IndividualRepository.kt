package org.jdc.template.model.repository

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import org.jdc.template.model.db.main.MainDatabaseWrapper
import org.jdc.template.model.db.main.directoryitem.DirectoryItem
import org.jdc.template.model.db.main.household.Household
import org.jdc.template.model.db.main.individual.Individual
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IndividualRepository
@Inject constructor(
    private val mainDatabaseWrapper: MainDatabaseWrapper,
    private val settingsRepository: SettingsRepository
) {
    private fun mainDatabase() = mainDatabaseWrapper.getDatabase()

    private fun individualDao() = mainDatabase().individualDao()
    private fun householdDao() = mainDatabase().householdDao()
    private fun directoryItemDao() = mainDatabase().directoryItemDao()

    fun getDirectoryListFlow(): Flow<List<DirectoryItem>> = settingsRepository.directorySortByLastNameFlow.flatMapLatest { byLastName ->
        when {
            byLastName -> directoryItemDao().findAllDirectItemsByLastNameFlow()
            else -> directoryItemDao().findAllDirectItemsByFirstNameFlow()
        }
    }
    fun setDirectorySort(byLastName: Boolean) {
        settingsRepository.setSortByLastNameAsync(byLastName)
    }
    suspend fun getIndividual(individualId: String): Individual? = individualDao().findById(individualId)
    fun getIndividualFlow(individualId: String): Flow<Individual> = individualDao().findByIdFlow(individualId)
    suspend fun getAllIndividuals(): List<Individual> = individualDao().findAll()
    suspend fun getIndividualCount(): Int = individualDao().findCount()
    suspend fun getIndividualFirstName(individualId: String): String? = individualDao().findFirstName(individualId)

    suspend fun saveIndividual(individual: Individual) {
        individualDao().insert(individual)
    }

    suspend fun saveIndividuals(individualList: List<Individual>) {
        mainDatabase().withTransaction {
            individualList.forEach { individual ->
                individualDao().insert(individual)
            }
        }
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
}