package org.jdc.template.shared.model.db.main.household

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface HouseholdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(householdEntity: HouseholdEntity): Long

    @Query("DELETE FROM Household")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM Household")
    suspend fun findAllMembers(): List<HouseholdMembers>
}
