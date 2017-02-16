/*
 * Individual.kt
 *
 * Created: 02/14/2016 07:02:10
 */



package org.jdc.template.model.database.main.individual


class Individual : IndividualBaseRecord() {

    fun getFullName(): String {
        return firstName + " " + lastName
    }
}