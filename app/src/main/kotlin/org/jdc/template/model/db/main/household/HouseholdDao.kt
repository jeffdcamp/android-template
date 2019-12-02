package org.jdc.template.model.db.main.household

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HouseholdDao {
    @Insert
    suspend fun insert(household: Household): Long

    @Insert
    suspend fun update(household: Household)

    @Query("DELETE FROM Household")
    suspend fun deleteAll()

    @Query("SELECT * FROM Household")
    suspend fun findAllMembers(): List<HouseholdMembers>
}
