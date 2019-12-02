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

    @Query("DELETE FROM Individual")
    suspend fun deleteAll()

    @Query("SELECT count(1) FROM Individual")
    suspend fun findCount(): Long

    @Query("SELECT * FROM Individual WHERE id = :id")
    suspend fun findById(id: Long): Individual?

    @Query("SELECT * FROM Individual WHERE id = :id")
    fun findByIdFlow(id: Long): Flow<Individual>

    @Query("SELECT * FROM Individual")
    suspend fun findAll(): List<Individual>

    @Query("SELECT * FROM Individual")
    fun findAllFlow(): Flow<List<Individual>>

    @Query("DELETE FROM Individual WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT lastModified FROM Individual WHERE id = :id")
    suspend fun findLastModified(id: Long): OffsetDateTime?

    @Query("SELECT firstName FROM Individual WHERE id = :id")
    suspend fun findFirstName(id: Long): String?

    @Query("UPDATE individual SET firstName = :firstName WHERE id = :id")
    suspend fun updateFirstName(id: Long, firstName: String)
}
