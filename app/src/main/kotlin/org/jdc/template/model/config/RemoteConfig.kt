package org.jdc.template.model.config

import org.jdc.template.R

class RemoteConfig : BaseFirebaseRemoteConfig() {

    // app update
    fun isColorServiceEnabled(): Boolean = getBoolean("colorServiceEnabled")

    fun showAllValues(): String {
        return """
            * colorServiceEnabled: ${isColorServiceEnabled()}
        """.trimIndent()
    }

    override fun getDefaults(): Int = R.xml.remote_config_defaults
}
