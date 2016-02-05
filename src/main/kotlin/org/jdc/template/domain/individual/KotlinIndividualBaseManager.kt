/*
 * IndividualBaseManager.java
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.jdc.template.domain.individual

import org.dbtools.android.domain.DBToolsEventBus
import org.dbtools.android.domain.RxAndroidBaseManagerWritable
import org.dbtools.android.domain.database.DatabaseWrapper
import org.jdc.template.domain.DatabaseManager
import org.jdc.template.domain.main.individual.Individual
import javax.inject.Inject


@SuppressWarnings("all")
abstract class KotlinIndividualBaseManager : RxAndroidBaseManagerWritable<Individual>() {

    @Inject
    lateinit var databaseManager: DatabaseManager

    @Inject
    lateinit var injectedBus: DBToolsEventBus

    override fun getDatabaseName(): String {
        return Individual.DATABASE
    }

    override fun newRecord(): Individual {
        return Individual()
    }

    override fun getTableName(): String {
        return Individual.TABLE
    }

    override fun getAllKeys(): Array<String> {
        return Individual.ALL_KEYS
    }

    override fun getReadableDatabase(databaseName: String): DatabaseWrapper<Any> {
        return databaseManager.getReadableDatabase(databaseName)
    }

    val readableDatabase: DatabaseWrapper<Any>
        get() = databaseManager.getReadableDatabase(databaseName)

    override fun getWritableDatabase(databaseName: String): DatabaseWrapper<Any> {
        return databaseManager.getWritableDatabase(databaseName)
    }

    val writableDatabase: DatabaseWrapper<Any>
        get() = databaseManager.getWritableDatabase(databaseName)

    override fun getAndroidDatabase(databaseName: String): org.dbtools.android.domain.AndroidDatabase? {
        return databaseManager.getDatabase(databaseName)
    }

    override fun getPrimaryKey(): String {
        return Individual.PRIMARY_KEY_COLUMN
    }

    override fun getDropSql(): String {
        return Individual.DROP_TABLE
    }

    override fun getCreateSql(): String {
        return Individual.CREATE_TABLE
    }

    override fun getBus(): org.dbtools.android.domain.DBToolsEventBus? {
        return injectedBus
    }

    fun setBus(bus: org.dbtools.android.domain.DBToolsEventBus) {
        this.injectedBus = bus
    }


}