/*
 * Individual.kt
 *
 * Created: 02/14/2016 07:02:10
 */



package org.jdc.template.model.database.main.individual

import android.content.ContentValues
import android.database.Cursor


class Individual : IndividualBaseRecord {


    constructor() {
    }

    constructor(cursor: Cursor) {
        setContent(cursor)
    }

    constructor(values: ContentValues) {
        setContent(values)
    }

    fun getFullName(): String {
        return firstName + " " + lastName
    }
}