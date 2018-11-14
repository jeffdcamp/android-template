package org.jdc.template.model.db.main.directoryitem

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface DirectoryItemDao {
    @Query("SELECT id, lastName, firstName FROM individual ORDER BY lastName, firstName")
    fun findAllDirectItemsLiveData(): LiveData<List<DirectoryItem>>
}
