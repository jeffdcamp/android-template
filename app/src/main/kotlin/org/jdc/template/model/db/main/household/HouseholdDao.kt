package org.jdc.template.model.db.main.household

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HouseholdDao {
    @Insert
    fun insert(household: Household): Long

    @Insert
    fun update(household: Household)

    @Query("DELETE FROM household")
    fun deleteAll()

    @Query("SELECT * FROM household")
    fun findAllMembers(): List<HouseholdMembers>

    @Query("SELECT * FROM household")
    fun findAllMembersLiveData(): LiveData<List<HouseholdMembers>>
}
