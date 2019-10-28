package org.jdc.template.model.db.main.individual

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.OffsetDateTime

@Dao
interface IndividualDao {
    @Insert
    suspend fun insert(individual: Individual): Long

    @Update
    suspend fun update(individual: Individual)

    @Query("DELETE FROM individual")
    suspend fun deleteAll()

    @Query("SELECT count(1) FROM individual")
    suspend fun findCount(): Long

    @Query("SELECT * FROM individual WHERE id = :id")
    suspend fun findById(id: Long): Individual?

    @Query("SELECT * FROM individual WHERE id = :id")
    fun findByIdFlow(id: Long): Flow<Individual>

    @Query("SELECT * FROM individual")
    suspend fun findAll(): List<Individual>

    @Query("SELECT * FROM individual")
    fun findAllFlow(): Flow<List<Individual>>

    @Query("DELETE FROM individual WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT lastModified FROM individual WHERE id = :id")
    suspend fun findLastModified(id: Long): OffsetDateTime?

    @Query("SELECT firstName FROM individual WHERE id = :id")
    suspend fun findFirstName(id: Long): String?

    @Query("UPDATE individual SET firstName = :firstName WHERE id = :id")
    suspend fun updateFirstName(id: Long, firstName: String)
}
