package org.jdc.template.model.db.main.chatthread

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.jdc.template.model.domain.ChatThreadListItem
import org.jdc.template.model.domain.inline.ChatThreadId

@Dao
interface ChatThreadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ChatThreadEntity): Long

    @Query("SELECT * FROM ChatThread WHERE id = :id")
    suspend fun findById(id: ChatThreadId): ChatThreadEntity?

    @Query("DELETE FROM ChatThread WHERE id = :id")
    suspend fun deleteById(id: ChatThreadId)

    @Query("SELECT * FROM ChatThread ORDER BY lastModified DESC")
    fun findAllFlow(): Flow<List<ChatThreadEntity>>

    @Query("SELECT id, name FROM ChatThread ORDER BY lastModified DESC")
    fun findAllChatThreadListItemFlow(): Flow<List<ChatThreadListItem>>
}
