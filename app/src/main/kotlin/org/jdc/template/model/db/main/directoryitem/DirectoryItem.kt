package org.jdc.template.model.db.main.directoryitem

import androidx.room.DatabaseView

@DatabaseView(
    viewName = DirectoryItem.VIEW_NAME,
    value = DirectoryItem.VIEW_QUERY
)
data class DirectoryItem(
    val individualId: String,
    val firstName: String,
    val lastName: String?
) {
    fun getFullName() = "$firstName ${lastName.orEmpty()}"

    companion object {
        const val VIEW_NAME = "DirectoryItem"
        const val VIEW_QUERY = "SELECT id AS individualId, lastName, firstName FROM Individual"
    }
}