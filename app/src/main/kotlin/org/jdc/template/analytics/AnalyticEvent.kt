package org.jdc.template.analytics

data class AnalyticEvent(
    val id: String,
    val scopeLevel: AppAnalytics.ScopeLevel = AppAnalytics.ScopeLevel.DEV,
    val params: Map<String, String>? = null,
)

data class AnalyticScreen(
    val screenTitle: String,
    val scopeLevel: AppAnalytics.ScopeLevel = AppAnalytics.ScopeLevel.DEV,
    val params: Map<String, String>? = null,
)

data class AnalyticError(
    val errorClass: String,
    val message: String,
    val scopeLevel: AppAnalytics.ScopeLevel = AppAnalytics.ScopeLevel.DEV,
)
