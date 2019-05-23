@file:Suppress("MemberVisibilityCanBePrivate")

import java.io.File
import java.io.FileInputStream
import java.util.Properties

object VersionCode {
    /**
     * Read the versionCode for a specific app.
     *
     * Be sure to define the following ABOVE defaultConfig {...}:
     * ext.versionCodeAppName = "my-app"
     * ext.minVersionCode = 100
     *
     * Set versionCode in defaultConfig {...}:
     * versionCode readVersionCode()
     *
     * @param increment Increments the version code (NOTE: This will increment AFTER configuration is executed (after defaultConfig {...}) (next build will have the incremented value)
     * @return current versionCode
     */
    fun readVersionCode(versionCodeAppName: String, minVersionCode: Int, increment: Boolean = false): Int {
        val versionsFilename = "$versionCodeAppName.properties"
        val versionCodesDirname = "${System.getProperty("user.home")}/.app-version-codes"

        // prepare directory
        val versionsDir = File(versionCodesDirname)
        if (!versionsDir.exists() && !versionsDir.mkdirs()) {
            throw IllegalStateException("Cannot create versions directory [${versionsDir.absolutePath}]")
        }

        // read existing versions file (create if does not exist)
        val versionPropsFile = File(versionsDir, versionsFilename)
        val versionProps = Properties()
        if (versionPropsFile.canRead()) {
            val reader = FileInputStream(versionPropsFile)
            versionProps.load(reader)
            reader.close()
        } else {
            println("Failed to read properties file [${versionPropsFile.absolutePath}]")
            versionProps["VERSION_CODE"] = minVersionCode.toString()
        }

        // get the existing version
        var versionCode = (versionProps["VERSION_CODE"] as String).toInt()

        // make sure the version code is GREATER THAN the minVersionCode
        if (versionCode < minVersionCode) {
            versionCode = minVersionCode
        }

        println("Current build will use versionCode [$versionCode]")

        if (increment) {
            // increment version code
            versionCode++

            // write updated version code
            versionPropsFile.writeText("VERSION_CODE=$versionCode")

            println("Incremented versionCode to [$versionCode]")
        }

        return versionCode
    }

    fun incrementVersionCode(versionCodeAppName: String, minVersionCode: Int) {
        readVersionCode(versionCodeAppName, minVersionCode, true)
    }
}