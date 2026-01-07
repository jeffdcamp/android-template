package org.jdc.template.shared

import io.ktor.client.engine.HttpClientEngine

expect fun platform(): String
expect fun httpClientEngine(): HttpClientEngine
