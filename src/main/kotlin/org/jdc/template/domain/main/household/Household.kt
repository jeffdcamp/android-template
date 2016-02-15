/*
 * Household.kt
 *
 * Created: 02/14/2016 07:02:10
 */



package org.jdc.template.domain.main.household

import android.database.Cursor
import android.content.ContentValues


class Household : HouseholdBaseRecord {


    constructor() {
    }

    constructor(cursor: Cursor) {
        setContent(cursor)
    }

    constructor(values: ContentValues) {
        setContent(values)
    }


}