package org.jdc.template.shared.model.db.main.household

import androidx.room3.ColumnInfo
import androidx.room3.Relation
import org.jdc.template.shared.model.db.main.individual.IndividualEntity

data class HouseholdMembers(
    var id: String = "",
    @ColumnInfo(name = "name")
    var householdName: String = "",

    @Relation(parentColumns = ["id"], entityColumns = ["householdId"], entity = IndividualEntity::class, projection = ["firstName"])
    var memberNames: List<String> = emptyList()
)
