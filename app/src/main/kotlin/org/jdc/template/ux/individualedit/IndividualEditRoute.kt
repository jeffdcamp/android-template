package org.jdc.template.ux.individualedit

import kotlinx.serialization.Serializable
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.ui.navigation.NavigationRoute
import org.jdc.template.ui.navigation.NavTypeMaps
import kotlin.reflect.typeOf

@Serializable
data class IndividualEditRoute(
    val individualId: IndividualId? = null
): NavigationRoute

fun IndividualEditRoute.Companion.typeMap() = mapOf(
    typeOf<IndividualId?>() to NavTypeMaps.IndividualIdNullableNavType,
)
