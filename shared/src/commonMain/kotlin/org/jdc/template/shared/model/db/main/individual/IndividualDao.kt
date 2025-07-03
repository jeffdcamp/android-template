package org.jdc.template.shared.model.db.main.individual

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant
import org.jdc.template.shared.model.domain.inline.IndividualId

@Dao
interface IndividualDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(individualEntity: IndividualEntity): Long

    @Query("DELETE FROM Individual")
    suspend fun deleteAll()

    @Query("SELECT count(1) FROM Individual")
    suspend fun findCount(): Int

    @Query("SELECT * FROM Individual WHERE id = :id")
    suspend fun findById(id: IndividualId): IndividualEntity?

    @Query("SELECT * FROM Individual WHERE id = :id")
    fun findByIdFlow(id: IndividualId): Flow<IndividualEntity?>

    @Query("SELECT * FROM Individual")
    suspend fun findAll(): List<IndividualEntity>

    @Query("SELECT * FROM Individual")
    fun findAllFlow(): Flow<List<IndividualEntity>>

    @Query("DELETE FROM Individual WHERE id = :id")
    suspend fun deleteById(id: IndividualId)

    @Query("SELECT lastModified FROM Individual WHERE id = :id")
    suspend fun findLastModified(id: IndividualId): Instant?

    @Query("SELECT firstName FROM Individual WHERE id = :id")
    suspend fun findFirstName(id: IndividualId): String?

    @Query("UPDATE individual SET firstName = :firstName WHERE id = :id")
    suspend fun updateFirstName(id: IndividualId, firstName: String)
}
