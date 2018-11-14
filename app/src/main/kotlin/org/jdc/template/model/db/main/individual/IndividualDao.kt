package org.jdc.template.model.db.main.individual

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
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

    @Query("DELETE FROM individual WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT lastModified FROM individual WHERE id = :id")
    @TypeConverters(DateTimeLongConverter::class)
    fun findLastModified(id: Long): LocalDateTime?

    @Query("SELECT firstName FROM individual WHERE id = :id")
    fun findFirstName(id: Long): String?

    @Query("UPDATE individual SET firstName = :firstName WHERE id = :id")
    fun updateFirstName(id: Long, firstName: String)
}
