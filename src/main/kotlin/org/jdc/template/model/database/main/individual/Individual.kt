/*
 * Individual.kt
 *
 * Created: 02/14/2016 07:02:10
 */



package org.jdc.template.model.database.main.individual

import android.database.Cursor
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues


class Individual : IndividualBaseRecord {


    constructor() {
    }

    constructor(cursor: Cursor) {
        setContent(cursor)
    }

    constructor(values: DBToolsContentValues<*>) {
        setContent(values)
    }

    fun getFullName(): String {
        return firstName + " " + lastName
    }
}