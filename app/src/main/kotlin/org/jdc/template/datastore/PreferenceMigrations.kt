@file:Suppress("unused")

package org.jdc.template.datastore

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.toMutablePreferences
import androidx.datastore.preferences.core.toPreferences
import timber.log.Timber
import java.util.TreeMap

val PREFERENCES_VERSION_KEY: Preferences.Key<Int> get() = preferencesKey("preferenceVersion")

/**
 * Versioned Migration support for DataStore preferences
 *
 * Example:
 *
 * object DevicePreferenceMigration2: PreferenceMigration(1, 2) {
 *     override suspend fun migrate(currentData: Preferences): Preferences {
 *         val mutablePreferences = currentData.toMutablePreferences()
 *
 *         // do migration here
 *
 *         return mutablePreferences.toPreferences()
 *     }
 * }
 *
 *
 * private val dataStore: DataStore<Preferences> = application.createDataStore(
 *     name = "device",
 *     migrations = listOf(
 *         // Migrate legacy SharedPrefs to version 1
 *         PreferenceMigrations.sharedPreferenceMigration(application, toVersion = 1, migrate = { sharedPrefs, currentData ->
 *             SharedPreferenceMigration.migrateSharedPreferences(sharedPrefs, currentData)
 *         }),
 *
 *         // Migrate DataStore Preferences to version 3
 *         PreferenceMigrations(3, listOf(DevicePreferenceMigration2, DevicePreferenceMigration1To3, DevicePreferenceMigration3))
 *     )
 * )
 *
 * @param version Target version (version MUST be greater than 0)
 * @param migrations Migrations to run
 * @param destructiveFallback Clear all preference data if there is no matching migrations
 */
class PreferenceMigrations(
    private val version: Int,
    migrations: List<PreferenceMigration>,
    private val destructiveFallback: Boolean = false,
): DataMigration<Preferences> {

    private val migrationTree = mutableMapOf<Int, TreeMap<Int, PreferenceMigration>>()

    init {
        migrations.forEach {
            val fromVersion = it.fromVersion
            val toVersion = it.toVersion

            var targetMap = migrationTree[fromVersion]
            if (targetMap == null) {
                targetMap = TreeMap()
                migrationTree[fromVersion] = targetMap
            }

            val existing = targetMap[toVersion]
            if (existing != null) {
                Timber.w("Overriding migration $existing with $migrations")
            }

            targetMap[toVersion] = it
        }
    }

    override suspend fun shouldMigrate(currentData: Preferences): Boolean {
        return (currentData[PREFERENCES_VERSION_KEY] ?: 0) != version
    }

    override suspend fun migrate(currentData: Preferences): Preferences {
        val fromVersion = (currentData[PREFERENCES_VERSION_KEY] ?: 0)
        val migrationsToRun = findMigrationPath(fromVersion, version)

        var data = currentData
        migrationsToRun.forEach { migration ->
            Timber.d("Migrating DataStore Preferences from version [${migration.fromVersion}] to [${migration.toVersion}]")
            data = migration.migrate(data)
        }

        val mutablePreferences = data.toMutablePreferences()

        if (fromVersion != 0 && destructiveFallback && migrationsToRun.isEmpty()) {
            mutablePreferences.clear()
            Timber.w("Destructive migration performed")
        }

        mutablePreferences[PREFERENCES_VERSION_KEY] = version

        Timber.d("DataStore Preferences set to version: [$version]")

        return mutablePreferences.toPreferences()
    }

    override suspend fun cleanUp() {
        // Do nothing
    }

    private fun findMigrationPath(fromVersion: Int, toVersion: Int): List<PreferenceMigration> {
        if (fromVersion == toVersion) {
            return emptyList()
        }
        val migrateUp = toVersion > fromVersion
        return findUpMigrationPath(mutableListOf(), migrateUp, fromVersion, toVersion)
    }

    private fun findUpMigrationPath(
        result: MutableList<PreferenceMigration>,
        upgrade: Boolean,
        fromVersion: Int,
        toVersion: Int
    ): List<PreferenceMigration> {
        var start = fromVersion
        while (if (upgrade) start < toVersion else start > toVersion) {
            val targetNodes: TreeMap<Int, PreferenceMigration> = migrationTree[start] ?: return emptyList()
            // keys are ordered so we can start searching from one end of them.
            var keySet: Set<Int>
            keySet = if (upgrade) {
                targetNodes.descendingKeySet()
            } else {
                targetNodes.keys
            }
            var found = false
            for (targetVersion in keySet) {
                val shouldAddToPath: Boolean = if (upgrade) {
                    targetVersion in (start + 1)..toVersion
                } else {
                    targetVersion in toVersion until start
                }
                if (shouldAddToPath) {
                    val target = checkNotNull(targetNodes[targetVersion]) { "Target was null... should NEVER happen" }
                    result.add(target)
                    start = targetVersion
                    found = true
                    break
                }
            }
            if (!found) {
                return emptyList()
            }
        }
        return result
    }

    companion object {
        fun sharedPreferenceMigration(
            context: Context,
            toVersion: Int,
            sharedPreferencesName: String = context.packageName + "_preferences", // default provided by PreferenceManager.getDefaultSharedPreferences(context)
            keysToMigrate: Set<String>? = null,
            deleteEmptyPreferences: Boolean = true,
            shouldRunMigration: suspend (Preferences) -> Boolean = { it[PREFERENCES_VERSION_KEY] == null },
            migrate: suspend (SharedPreferencesView, Preferences) -> Preferences
        ) = SharedPreferencesMigration(context, sharedPreferencesName, keysToMigrate, deleteEmptyPreferences, shouldRunMigration, migrate = { sharedPrefs, currentData ->
            val mutablePreferences = migrate(sharedPrefs, currentData).toMutablePreferences()

            mutablePreferences[PREFERENCES_VERSION_KEY] = toVersion

            mutablePreferences.toPreferences()
        })
    }
}

abstract class PreferenceMigration(val fromVersion: Int, val toVersion: Int) {
    abstract suspend fun migrate(currentData: Preferences): Preferences
}