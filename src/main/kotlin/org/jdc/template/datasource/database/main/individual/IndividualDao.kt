package org.jdc.template.datasource.database.main.individual

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface IndividualDao {
    @Insert
    fun insert(individual: Individual)

    @Update
    fun update(individual: Individual)

    @Query("DELETE FROM individual")
    fun deleteAll()

    @Query("SELECT count(1) FROM individual")
    fun findCount(): Long

    @Query("SELECT * FROM individual WHERE id = :p0")
    fun findById(p0: Long): Individual?

    @Query("SELECT * FROM individual WHERE id = :p0")
    fun findByIdLive(p0: Long): LiveData<Individual>

    @Query("SELECT * FROM individual")
    fun findAll(): List<Individual>

    @Query("SELECT * FROM individual")
    fun findAllLive(): LiveData<List<Individual>>

    @Query("SELECT id, lastName, firstName FROM individual ORDER BY lastName, firstName")
    fun findAllDirectListItemsLive(): LiveData<List<DirectoryListItem>>

    @Query("DELETE FROM individual WHERE id = :p0")
    fun deleteById(p0: Long)

    data class DirectoryListItem(val id: Long, val firstName: String, val lastName: String) {
        fun getFullName() = firstName + " " + lastName
    }
}
