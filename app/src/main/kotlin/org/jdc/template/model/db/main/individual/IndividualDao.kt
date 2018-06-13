package org.jdc.template.model.db.main.individual

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.Update
import org.jdc.template.model.db.converter.DateTimeLongConverter
import org.threeten.bp.LocalDateTime

@Dao
interface IndividualDao {
    @Insert
    fun insert(individual: Individual): Long

    @Update
    fun update(individual: Individual)

    @Query("DELETE FROM individual")
    fun deleteAll()

    @Query("SELECT count(1) FROM individual")
    fun findCount(): Long

    @Query("SELECT * FROM individual WHERE id = :id")
    fun findById(id: Long): Individual?

    @Query("SELECT * FROM individual WHERE id = :id")
    fun findByIdLiveData(id: Long): LiveData<Individual>

    @Query("SELECT * FROM individual")
    fun findAll(): List<Individual>

    @Query("SELECT * FROM individual")
    fun findAllLiveData(): LiveData<List<Individual>>

    @Query("SELECT id, lastName, firstName FROM individual ORDER BY lastName, firstName")
    fun findAllDirectListItemsLiveData(): LiveData<List<DirectoryListItem>>

    @Query("DELETE FROM individual WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT lastModified FROM individual WHERE id = :id")
    @TypeConverters(DateTimeLongConverter::class)
    fun findLastModified(id: Long): LocalDateTime?

    data class DirectoryListItem(val id: Long, val firstName: String, val lastName: String) {
        fun getFullName() = firstName + " " + lastName
    }
}
