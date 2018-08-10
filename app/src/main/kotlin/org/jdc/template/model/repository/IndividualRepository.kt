package org.jdc.template.model.repository

import org.jdc.template.model.db.main.MainDatabase
import org.jdc.template.model.db.main.household.Household
import org.jdc.template.model.db.main.individual.Individual
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IndividualRepository
@Inject constructor(
    private val mainDatabase: MainDatabase
){
    private fun individualDao() = mainDatabase.individualDao
    private fun householdDao() = mainDatabase.householdDao

    fun getDirectoryListLiveData() = individualDao().findAllDirectListItemsLiveData()
    fun getIndividual(individualId: Long) = individualDao().findById(individualId)
    fun getIndividualLiveData(individualId: Long) = individualDao().findByIdLiveData(individualId)
    fun getAllIndividuals() = individualDao().findAll()
    fun getIndividualCount() = individualDao().findCount()
    fun getIndividualFirstName(individualId: Long) = individualDao().findFirstName(individualId)

    fun saveIndividual(individual: Individual) {
        if (individual.id <= 0) {
            val newId = individualDao().insert(individual)
            individual.id = newId
        } else {
            individualDao().update(individual)
        }
    }

    fun saveHousehold(household: Household) {
        if (household.id <= 0) {
            val newId = householdDao().insert(household)
            household.id = newId
        } else {
            householdDao().update(household)
        }
    }

    fun saveNewHousehold(lastName: String, individuals: List<Individual>) {
        mainDatabase.runInTransaction {
            val household = Household()
            household.name = lastName
            saveHousehold(household)

            individuals.forEach { individual ->
                individual.householdId = household.id
                saveIndividual(individual)
            }
        }
    }

    fun deleteIndividual(individualId: Long) = individualDao().deleteById(individualId)

    fun deleteAllIndividuals() {
        mainDatabase.runInTransaction {
            individualDao().deleteAll()
            householdDao().deleteAll()
        }
    }
}