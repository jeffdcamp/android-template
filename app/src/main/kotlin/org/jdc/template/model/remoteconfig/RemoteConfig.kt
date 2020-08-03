package org.jdc.template.model.remoteconfig

import org.jdc.template.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfig
@Inject constructor(
) : BaseFirebaseRemoteConfig(R.xml.remote_config_defaults) {

    // app update
    fun isColorServiceEnabled(): Boolean = getBoolean("colorServiceEnabled")

    fun showAllValues(): String {
        return """
            * colorServiceEnabled: ${isColorServiceEnabled()}
        """.trimIndent()
    }
}
