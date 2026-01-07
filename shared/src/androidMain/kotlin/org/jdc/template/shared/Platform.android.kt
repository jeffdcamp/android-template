package org.jdc.template.shared

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun platform() = "Android"
actual fun httpClientEngine(): HttpClientEngine = OkHttp.create()
