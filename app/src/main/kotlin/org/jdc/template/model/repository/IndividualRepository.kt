package org.jdc.template.model.repository

import org.dbtools.android.room.ext.runInTransactionSuspend
import org.jdc.template.model.db.main.MainDatabaseWrapper
import org.jdc.template.model.db.main.household.Household
import org.jdc.template.model.db.main.individual.Individual
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IndividualRepository
@Inject constructor(
    private val mainDatabaseWrapper: MainDatabaseWrapper
){
    private fun mainDatabase() = mainDatabaseWrapper.getDatabase()

    private fun individualDao() = mainDatabase().individualDao
    private fun householdDao() = mainDatabase().householdDao
    private fun directoryItemDao() = mainDatabase().directoryItemDao

    fun getDirectoryListLiveData() = directoryItemDao().findAllDirectItemsLiveData()
    suspend fun getIndividual(individualId: Long) = individualDao().findById(individualId)
    fun getIndividualLiveData(individualId: Long) = individualDao().findByIdLiveData(individualId)
    suspend fun getAllIndividuals() = individualDao().findAll()
    suspend fun getIndividualCount() = individualDao().findCount()
    suspend fun getIndividualFirstName(individualId: Long) = individualDao().findFirstName(individualId)

    suspend fun saveIndividual(individual: Individual) {
        if (individual.id <= 0) {
            val newId = individualDao().insert(individual)
            individual.id = newId
        } else {
            individualDao().update(individual)
        }
    }

    suspend fun saveHousehold(household: Household) {
        if (household.id <= 0) {
            val newId = householdDao().insert(household)
            household.id = newId
        } else {
            householdDao().update(household)
        }
    }

    suspend fun saveNewHousehold(lastName: String, individuals: List<Individual>) {
        mainDatabase().runInTransactionSuspend {
            val household = Household()
            household.name = lastName
            saveHousehold(household)

            individuals.forEach { individual ->
                individual.householdId = household.id
                saveIndividual(individual)
            }
        }
    }

    suspend fun deleteIndividual(individualId: Long) = individualDao().deleteById(individualId)

    suspend fun deleteAllIndividuals() {
        mainDatabase().runInTransactionSuspend {
            individualDao().deleteAll()
            householdDao().deleteAll()
        }
    }

    suspend fun getAllMembers() = householdDao().findAllMembers()
}