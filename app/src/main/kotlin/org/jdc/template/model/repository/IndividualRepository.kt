package org.jdc.template.model.repository

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.jdc.template.model.db.main.MainDatabaseWrapper
import org.jdc.template.model.db.main.directoryitem.DirectoryItemEntityView
import org.jdc.template.model.db.main.household.HouseholdEntity
import org.jdc.template.model.db.main.individual.IndividualEntity
import org.jdc.template.model.domain.Household
import org.jdc.template.model.domain.Individual
import org.jdc.template.model.domain.inline.CreatedTime
import org.jdc.template.model.domain.inline.Email
import org.jdc.template.model.domain.inline.FirstName
import org.jdc.template.model.domain.inline.HouseholdId
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.model.domain.inline.LastModifiedTime
import org.jdc.template.model.domain.inline.LastName
import org.jdc.template.model.domain.inline.Phone
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
    suspend fun getIndividual(individualId: IndividualId): Individual? = individualDao().findById(individualId.value)?.toIndividual()
    fun getIndividualFlow(individualId: IndividualId): Flow<Individual> = individualDao().findByIdFlow(individualId.value).map { it.toIndividual() }
    suspend fun getAllIndividuals(): List<Individual> = individualDao().findAll().map { it.toIndividual() }
    suspend fun getIndividualCount(): Int = individualDao().findCount()
    suspend fun getIndividualFirstName(individualId: IndividualId): String? = individualDao().findFirstName(individualId.value)

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

    suspend fun deleteIndividual(individualId: IndividualId) = individualDao().deleteById(individualId.value)

    suspend fun deleteAllIndividuals() {
        mainDatabase().withTransaction {
            individualDao().deleteAll()
            householdDao().deleteAll()
        }
    }
}

fun Household.toEntity(): HouseholdEntity {
    return HouseholdEntity(
        id = id.value,
        name = name.value,

        created = lastModified.value,
        lastModified = lastModified.value,
    )
}

fun HouseholdEntity.toHousehold(): Household {
    return Household(
        id = HouseholdId(id),
        name = LastName(name),
        created = CreatedTime(created),
        lastModified = LastModifiedTime(lastModified)
    )
}

private fun Individual.toEntity(): IndividualEntity {
    return IndividualEntity(
        id = id.value,
        householdId = householdId?.value,
        individualType = individualType,
        firstName = firstName?.value,
        lastName = lastName?.value,
        birthDate = birthDate,
        alarmTime = alarmTime,
        phone = phone?.value,
        email = email?.value,
        available = available,

        created = lastModified.value,
        lastModified = lastModified.value,
    )
}

private fun IndividualEntity.toIndividual(): Individual {
    return Individual(
        id = IndividualId(id),
        householdId = householdId?.let { HouseholdId(it) },
        individualType = individualType,
        firstName = firstName?.let { FirstName(it) },
        lastName = lastName?.let { LastName(it) },
        birthDate = birthDate,
        alarmTime = alarmTime,
        phone = phone?.let { Phone(it) },
        email = email?.let { Email(it) },
        available = available,
        created = CreatedTime(created),
        lastModified = LastModifiedTime(lastModified)
    )
}

