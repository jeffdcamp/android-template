package org.jdc.template.model.repository

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.jdc.template.model.db.main.MainDatabaseWrapper
import org.jdc.template.model.db.main.directoryitem.DirectoryItemEntityView
import org.jdc.template.model.db.main.household.HouseholdEntity
import org.jdc.template.model.db.main.individual.IndividualEntity
import org.jdc.template.model.domain.Household
import org.jdc.template.model.domain.Individual
import org.jdc.template.model.domain.inline.CreatedTime
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.model.domain.inline.LastModifiedTime
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

    fun getDirectoryListFlow(): Flow<List<DirectoryItemEntityView>> = settingsRepository.directorySortByLastNameFlow.flatMapLatest { byLastName ->
        when {
            byLastName -> directoryItemDao().findAllDirectItemsByLastNameFlow()
            else -> directoryItemDao().findAllDirectItemsByFirstNameFlow()
        }
    }
    fun setDirectorySort(byLastName: Boolean) {
        settingsRepository.setSortByLastNameAsync(byLastName)
    }
    suspend fun getIndividual(individualId: IndividualId): Individual? = individualDao().findById(individualId)?.toIndividual()
    fun getIndividualFlow(individualId: IndividualId): Flow<Individual?> = individualDao().findByIdFlow(individualId).filterNotNull().map { it.toIndividual() }
    suspend fun getAllIndividuals(): List<Individual> = individualDao().findAll().map { it.toIndividual() }
    suspend fun getIndividualCount(): Int = individualDao().findCount()
    suspend fun getIndividualFirstName(individualId: IndividualId): String? = individualDao().findFirstName(individualId)

    suspend fun saveIndividual(individual: Individual) {
        individualDao().insert(individual.toEntity())
    }

    suspend fun saveIndividuals(individualList: List<Individual>) {
        mainDatabase().withTransaction {
            individualList.forEach { individual ->
                individualDao().insert(individual.toEntity())
            }
        }
    }

    private suspend fun saveHousehold(household: Household) {
        householdDao().insert(household.toEntity())
    }

    suspend fun saveNewHousehold(household: Household, individuals: List<Individual>) {
        mainDatabase().withTransaction {
            saveHousehold(household)

            individuals.forEach { individual ->
                saveIndividual(individual.copy(householdId = household.id))
            }
        }
    }

    suspend fun deleteIndividual(individualId: IndividualId) = individualDao().deleteById(individualId)

    suspend fun deleteAllIndividuals() {
        mainDatabase().withTransaction {
            individualDao().deleteAll()
            householdDao().deleteAll()
        }
    }
}

fun Household.toEntity(): HouseholdEntity {
    return HouseholdEntity(
        id = id,
        name = name,

        created = lastModified.value,
        lastModified = lastModified.value,
    )
}

fun HouseholdEntity.toHousehold(): Household {
    return Household(
        id = id,
        name = name,
        created = CreatedTime(created),
        lastModified = LastModifiedTime(lastModified)
    )
}

private fun Individual.toEntity(): IndividualEntity {
    return IndividualEntity(
        id = id,
        householdId = householdId,
        individualType = individualType,
        firstName = firstName,
        lastName = lastName,
        birthDate = birthDate,
        alarmTime = alarmTime,
        phone = phone,
        email = email,
        available = available,

        created = lastModified.value,
        lastModified = lastModified.value,
    )
}

private fun IndividualEntity.toIndividual(): Individual {
    return Individual(
        id = id,
        householdId = householdId,
        individualType = individualType,
        firstName = firstName,
        lastName = lastName,
        birthDate = birthDate,
        alarmTime = alarmTime,
        phone = phone,
        email = email,
        available = available,
        created = CreatedTime(created),
        lastModified = LastModifiedTime(lastModified)
    )
}

