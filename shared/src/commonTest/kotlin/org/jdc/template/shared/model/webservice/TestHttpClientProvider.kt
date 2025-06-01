package org.jdc.template.shared.model.webservice

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.jdc.template.shared.model.webservice.KtorClientDefaults.defaultSetup

object TestHttpClientProvider {
    fun getTestClient(
        engine: HttpClientEngine,
        locales: () -> String = { "en-US" },
    ): HttpClient {
        return HttpClient(engine) {
            install(ResponseTimePlugin)
            install(Logging) {
                defaultSetup()
            }
            install(ContentNegotiation) {
                defaultSetup(allowAnyContentType = false)
            }
            install(Resources)

            defaultRequest {
                val currentLocales = locales()
                if (currentLocales.isNotBlank()) {
                    header(HttpHeaders.AcceptLanguage, currentLocales)
                }
            }
        }
    }
}