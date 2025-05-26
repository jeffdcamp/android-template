package org.jdc.template.shared.model.db.main.chatmessage

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.jdc.template.shared.model.domain.inline.ChatMessageId
import org.jdc.template.shared.model.domain.inline.ChatThreadId

@Dao
interface ChatMessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ChatMessageEntity): Long

    @Query("DELETE FROM ChatMessage WHERE id = :id")
    suspend fun deleteById(id: ChatMessageId)

    @Query("SELECT * FROM ChatMessage WHERE chatThreadId = :chatThreadId ORDER BY createdDate DESC")
    fun findAllPaging(chatThreadId: ChatThreadId): PagingSource<Int, ChatMessageEntity>
}
