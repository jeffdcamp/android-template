package org.jdc.template.shared.model.webservice

import co.touchlab.kermit.Logger
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiationConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.LoggingConfig
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.jdc.template.shared.util.log.KtorKermitLogger

/**
 * Project Specific Defaults (should not be in shared library)
 */
object KtorClientDefaults {
    fun LoggingConfig.defaultSetup() {
        logger = KtorKermitLogger
        level = LogLevel.INFO
        sanitizeHeader { header -> header == HttpHeaders.Authorization }
    }

    fun ContentNegotiationConfig.defaultSetup(
        allowAnyContentType: Boolean = false,
        jsonPrettyPrint: Boolean = false
    ) {
        val jsonDefinition = Json {
            prettyPrint = jsonPrettyPrint
            isLenient = true
            coerceInputValues = true // incorrect JSON values to the default
            ignoreUnknownKeys = true
        }

        json(jsonDefinition)

        // work-around for github that does NOT return Content-Type: application/json (github returns  Content-Type: text/plain; charset=utf-8)
        if (allowAnyContentType) {
            register(ContentType.Any, KotlinxSerializationConverter(jsonDefinition))
        }
    }
}

val ResponseTimePlugin = createClientPlugin("ResponseTimePlugin") {
    onResponse { response ->
        Logger.i {
            "<-- ${response.request.method.value} ${response.request.url} : ${response.responseTime.timestamp - response.requestTime.timestamp} ms"
        }
    }
}
