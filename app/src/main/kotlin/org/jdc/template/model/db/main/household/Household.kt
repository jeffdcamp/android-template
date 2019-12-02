package org.jdc.template.model.db.main.household

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Household(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = ""
)
