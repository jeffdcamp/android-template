package org.jdc.template.ux.individual

import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable
import org.jdc.template.model.domain.inline.IndividualId
import org.jdc.template.ui.navigation.NavTypeMaps
import org.jdc.template.ui.navigation.NavigationRoute
import org.jdc.template.ux.NavIntentFilterPart
import kotlin.reflect.typeOf

@Serializable
data class IndividualRoute(
    val individualId: IndividualId
): NavigationRoute

fun IndividualRoute.Companion.typeMap() = mapOf(
    typeOf<IndividualId>() to NavTypeMaps.IndividualIdNavType,
)

fun IndividualRoute.Companion.deepLinks() = listOf(
    // ./adb shell am start -W -a android.intent.action.VIEW -d "android-template://individual/xxxx"
    navDeepLink<IndividualRoute>(basePath = "${NavIntentFilterPart.DEFAULT_APP_SCHEME}://individual", typeMap = IndividualRoute.typeMap()),
)
