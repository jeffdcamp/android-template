package org.jdc.template.shared.model.db.main.directoryitem

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DirectoryItemDao {
    @Query("SELECT id AS individualId, lastName, firstName FROM Individual ORDER BY firstName, lastName")
    fun findAllDirectItemsByFirstNameFlow(): Flow<List<DirectoryItemEntityView>>

    @Query("SELECT id AS individualId, lastName, firstName FROM Individual ORDER BY lastName, firstName")
    fun findAllDirectItemsByLastNameFlow(): Flow<List<DirectoryItemEntityView>>
}
