package org.jdc.template.model.db.main.household

import androidx.room.ColumnInfo
import androidx.room.Relation
import org.jdc.template.model.db.main.individual.Individual

data class HouseholdMembers(
    var id: Long = 0L,
    @ColumnInfo(name = "name")
    var householdName: String = "",

//    @Relation(parentColumn = "id", entityColumn = "householdId")
//    var members: List<Individual> = emptyList(),

    @Relation(parentColumn = "id", entityColumn = "householdId", entity = Individual::class, projection = ["firstName"])
    var memberNames: List<String> = emptyList()
)


