package org.jdc.template.shared.util.log

import io.ktor.client.plugins.logging.Logger

object KtorKermitLogger : Logger {
    override fun log(message: String) {
        co.touchlab.kermit.Logger.i { message }
    }
}
