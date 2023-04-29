package org.jdc.template.model.db.main.individual

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime

@Dao
interface IndividualDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(individualEntity: IndividualEntity): Long

    @Query("DELETE FROM Individual")
    suspend fun deleteAll()

    @Query("SELECT count(1) FROM Individual")
    suspend fun findCount(): Int

    @Query("SELECT * FROM Individual WHERE id = :id")
    suspend fun findById(id: String): IndividualEntity?

    @Query("SELECT * FROM Individual WHERE id = :id")
    fun findByIdFlow(id: String): Flow<IndividualEntity>

    @Query("SELECT * FROM Individual")
    suspend fun findAll(): List<IndividualEntity>

    @Query("SELECT * FROM Individual")
    fun findAllFlow(): Flow<List<IndividualEntity>>

    @Query("DELETE FROM Individual WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT lastModified FROM Individual WHERE id = :id")
    suspend fun findLastModified(id: String): OffsetDateTime?

    @Query("SELECT firstName FROM Individual WHERE id = :id")
    suspend fun findFirstName(id: String): String?

    @Query("UPDATE individual SET firstName = :firstName WHERE id = :id")
    suspend fun updateFirstName(id: String, firstName: String)
}
