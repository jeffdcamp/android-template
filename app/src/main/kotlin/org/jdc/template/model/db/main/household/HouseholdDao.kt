package org.jdc.template.model.db.main.household

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface HouseholdDao {
    @Insert
    fun insert(household: Household): Long

    @Insert
    fun update(household: Household)

    @Query("DELETE FROM household")
    fun deleteAll()
}
