package org.jdc.template.shared.model.db.main.household

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction

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
