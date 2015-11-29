/*
 * Individual.java
 *
 * Created: 10/02/2012 01:40:29
 */



package org.jdc.template.domain.individual

import android.content.ContentValues
import android.database.Cursor


class KotlinIndividual : KotlinIndividualBaseRecord {

    constructor(cursor: Cursor) {
        setContent(cursor)
    }

    constructor(values: ContentValues) {
        setContent(values)
    }

    constructor() {
    }

    fun getFullName() = firstName + " " + lastName
}