package org.jdc.template.shared

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun platform(): String = "JVM"
actual fun httpClientEngine(): HttpClientEngine = OkHttp.create()