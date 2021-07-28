package org.jdc.template.model.db.main.household

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Household(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var name: String = ""
)
