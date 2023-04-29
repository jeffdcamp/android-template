package org.jdc.template.model.db.main.household

import androidx.room.ColumnInfo
import androidx.room.Relation
import org.jdc.template.model.db.main.individual.IndividualEntity

data class HouseholdMembers(
    var id: String = "",
    @ColumnInfo(name = "name")
    var householdName: String = "",

    @Relation(parentColumn = "id", entityColumn = "householdId", entity = IndividualEntity::class, projection = ["firstName"])
    var memberNames: List<String> = emptyList()
)
