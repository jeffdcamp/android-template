/*
 * IndividualList.kt
 *
 * Created: 02/14/2016 07:02:10
 */



package org.jdc.template.model.database.other.individuallist

import android.database.Cursor
import android.content.ContentValues


class IndividualList : IndividualListBaseRecord {


    constructor() {
    }

    constructor(cursor: Cursor) {
        setContent(cursor)
    }

    constructor(values: ContentValues) {
        setContent(values)
    }


}