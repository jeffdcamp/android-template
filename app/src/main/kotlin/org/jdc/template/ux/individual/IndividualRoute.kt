package org.jdc.template.ux.individual

import kotlinx.serialization.Serializable
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.ui.navigation.NavTypeMaps
import org.jdc.template.ui.navigation.NavigationRoute
import kotlin.reflect.typeOf

@Serializable
data class IndividualRoute(
    val individualId: IndividualId
): NavigationRoute

fun IndividualRoute.Companion.typeMap() = mapOf(
    typeOf<IndividualId>() to NavTypeMaps.IndividualIdNavType,
)
