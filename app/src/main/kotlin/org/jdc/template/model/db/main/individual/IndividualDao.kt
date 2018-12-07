package org.jdc.template.model.db.main.individual

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.threeten.bp.OffsetDateTime

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

    @Query("DELETE FROM individual WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT lastModified FROM individual WHERE id = :id")
    fun findLastModified(id: Long): OffsetDateTime?

    @Query("SELECT firstName FROM individual WHERE id = :id")
    fun findFirstName(id: Long): String?

    @Query("UPDATE individual SET firstName = :firstName WHERE id = :id")
    fun updateFirstName(id: Long, firstName: String)
}
