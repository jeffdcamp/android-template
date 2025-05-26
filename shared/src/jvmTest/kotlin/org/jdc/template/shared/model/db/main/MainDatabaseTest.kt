package org.jdc.template.shared.model.db.main

import androidx.room.Room
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import okio.FileSystem
import okio.Path.Companion.toPath
import org.dbtools.room.ext.deleteDatabaseFiles
import org.jdc.template.shared.model.domain.inline.FirstName
import org.jdc.template.shared.model.domain.inline.IndividualId
import org.jdc.template.shared.model.domain.inline.LastName
import org.jdc.template.shared.model.domain.type.IndividualType
import org.jdc.template.shared.model.db.main.individual.IndividualEntity
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class MainDatabaseTest {

    private val databasePath = "build/test/db/${MainDatabase.DATABASE_NAME}".toPath()

    @BeforeTest
    fun beforeTest() {
        FileSystem.SYSTEM.deleteDatabaseFiles(databasePath)
    }

    @Test
    fun basicTest() = runTest {
        val mainDatabase = MainDatabase.getDatabase(
            builder = Room.databaseBuilder<MainDatabase>(databasePath.toFile().absolutePath)
        )

        val individualId1 = IndividualId("1")
        val individualDao = mainDatabase.individualDao()
        val writeIndividual = IndividualEntity(
            id = individualId1,
            firstName = FirstName("Jeff"),
            lastName = LastName("Campbell"),
            individualType = IndividualType.HEAD,
            available = true,
            householdId = null,
            birthDate = null,
            alarmTime = null,
            phone = null,
            email = null,
            created = Clock.System.now(),
            lastModified = Clock.System.now(),
        )

        individualDao.insert(writeIndividual)

        val readIndividual = individualDao.findById(individualId1)
        assertNotNull(readIndividual)
        assertThat(readIndividual.firstName).isEqualTo(writeIndividual.firstName)

        mainDatabase.close()
    }
}
