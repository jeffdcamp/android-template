package org.jdc.template.model.webservice

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Project Specific Defaults (should not be in shared library)
 */
object KtorClientDefaults {
    fun Logging.Config.defaultSetup() {
        logger = KermitKtorLogger
        level = LogLevel.INFO
        sanitizeHeader { header -> header == HttpHeaders.Authorization }
    }

    fun ContentNegotiation.Config.defaultSetup(
        allowPlainTextJson: Boolean = false,
        jsonPrettyPrint: Boolean = false
    ) {
        val jsonDefinition = Json {
            prettyPrint = jsonPrettyPrint
            isLenient = true
            coerceInputValues = true // incorrect JSON values to the default
        }

        json(jsonDefinition)

        // work-around for github that does NOT return Content-Type: application/json (github returns  Content-Type: text/plain; charset=utf-8)
        if (allowPlainTextJson) {
            register(ContentType.Text.Plain, KotlinxSerializationConverter(jsonDefinition))
        }
    }
}

private object KermitKtorLogger : Logger {
    override fun log(message: String) {
        co.touchlab.kermit.Logger.i { message }
    }
}
